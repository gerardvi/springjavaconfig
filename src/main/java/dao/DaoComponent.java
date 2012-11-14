package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import commons.Entity;

@Component
public class DaoComponent {
    private static final String AST = "_x_";

    @PersistenceContext
    private EntityManager entityManager;

    private StringBuilder buffer;
    private ArrayList <Parameter> params;

    /** <b style="color:#E00000;font-size:20px;">Only to be called by spring.</b> */
    public DaoComponent () {
        this.buffer = new StringBuilder (0x1000); /* 4 kilo char: 8 kB, since a Java-char's size is 2 bytes.  */
        this.params = new ArrayList (32);
    }

    public long count (Class type, DataFilter dataFilter) {
        Entity.validate (type, "type");
        return ((Number) createQuery (Number.class, type, "count (*)", dataFilter).getSingleResult ()).longValue ();
    }

    @Transactional
    public void delete (Class type, long id) {
        Entity.validate (type, "type");
        if (id != 0) {
            Query query;
            synchronized (this) {
                buffer.setLength (0);
                buffer.append ("delete from ");
                buffer.append (type.getName ());
                buffer.append (" where id=:__param__");
                query = entityManager.createQuery (buffer.toString ());
            }
            query.setParameter ("__param__", Long.valueOf (id));
            query.executeUpdate ();
        }
    }

    public Entity read (Class type, long id) {
        Entity.validate (type, "type");
        return (Entity) entityManager.find (type, Long.valueOf(id));
    }

    public List <Entity> read (Class type, DataFilter dataFilter, int fromIndex, int max, String sortProp, boolean asc) {
        Entity.validate (type, "type");
        return createQuery (type, type, AST, dataFilter, fromIndex, max, sortProp, asc).getResultList ();
    }

    @Transactional
    public void write (Entity entity) {
        if (entity.isNew ()) {
            entityManager.persist (entity);
        } else {
            entityManager.merge (entity);
        }
    }

    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** *****
     * P R I V A T E
     * ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** *****/

    private Query createQuery (Class returnType, Class entityType, String toBeSelected, DataFilter dataFilter) {
        return createQuery (returnType, entityType, toBeSelected, dataFilter, -1, -1, null, false);
    }

    /* fromIndex<0 ignores it and the rest of the arguments. */
    private Query createQuery (Class returnType,
                               Class entityType,
                              String toBeSelected,
                          DataFilter dataFilter,
                                 int fromIndex,
                                 int max,
                              String sortProp,
                             boolean asc) {

        Query query;
        synchronized (this) {
            params.clear ();
            buffer.setLength (0);
            buffer.append ("select ");
            buffer.append (toBeSelected);
            buffer.append (" from ");
            buffer.append (entityType.getName ());
            buffer.append (' ');
            buffer.append (AST);

            if (dataFilter.size () > 0) {
                Iterator <IColumnConstraint> constraints = dataFilter.constraints ();
                buffer.append (" where ");
                writeConstraint (constraints.next ());
                while (constraints.hasNext ()) {
                    buffer.append (" and ");
                    writeConstraint (constraints.next ());
                }
            }

            if (fromIndex > -1) {
                buffer.append (" order by ");
                buffer.append (sortProp);
                if (!asc) {
                    buffer.append (" desc");
                }
            }

            query = entityManager.createQuery (buffer.toString (), returnType);
            for (int i=0; i<params.size (); ++i) {
                Parameter param = params.get (i);
                query.setParameter (param.name, param.value);
            }
        }
        if (fromIndex > -1) {
            query.setFirstResult (fromIndex);
            query.setMaxResults (max);
        }
        return query;
    }

    private void writeConstraint (IColumnConstraint constraint) {
        buffer.append (AST);
        buffer.append ('.');
        buffer.append (constraint.getColumn ());
        buffer.append (constraint.getOperator ());
        buffer.append (Parameter.add (params, constraint.getValue ()));
    }
}
