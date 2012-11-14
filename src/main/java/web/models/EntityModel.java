package web.models;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import services.ServiceComponent;

import commons.Beans;
import commons.Entity;

public class EntityModel implements IModel <Entity> {
    @SpringBean
    private ServiceComponent service;

    private transient Entity entity; /* TODO: Make sure that `entity' doesn't get stored on the session.
                                        I expect the model object to be stored on the session in serialized form:
                                        then the keyword `transient' saves me.
                                        Anyway, there must be another solution, in which this member can
                                        be removed entirely.  */

    private Class entityType;
    private long id;

    public EntityModel (Class entityType, long id) {
        Injector.get ().inject (this);
        this.entityType = entityType;
        this.id = id;
    }

    public EntityModel (Class entityType) {
        this (entityType, 0);
    }

    public EntityModel (Entity entity) {
        this (entity.getClass (), entity.getId ());
    }

    @Override
    public void detach () {
    }

    public Class getEntityType () {
        return entityType;
    }

    public long getId () {
        return id;
    }

    @Override
    public Entity getObject () {
        if (entity == null) {
            entity = id != 0 ? service.read (entityType, id) : (Entity) Beans.newInstance (entityType);
        }
        return entity;
    }

    public void setId (long val) {
        id = val;
    }

    @Override
    public void setObject (Entity object) {
        entityType = object.getClass ();
        id = object.getId ();
    }
}
