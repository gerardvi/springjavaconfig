package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import dao.IColumnConstraint.Operator;

public class DataFilter implements Serializable {
    public static final DataFilter EMPTY_DATA_FILTER = new EmptyDataFilter ();

    private final List <IColumnConstraint> list;

    private DataFilter (List <IColumnConstraint> list) {
        this.list = list;
    }

    public DataFilter () {
        this (new ArrayList (32));
    }

    public void add (String column, Operator operator, Object value) {
        if (value != null) {
            list.add (new ColumnConstraint (column, operator, value));
        }
    }

    public final void add (String column, Object value) {
        add (column, Operator.EQ, value);
    }

    public final Iterator <IColumnConstraint> constraints () {
        return list.iterator ();
    }

    public final void remove (String column) {
        for (int i = list.size () - 1;  i > -1;  --i) {
            if (list.get (i).getColumn ().equals (column)) {
                list.remove (i);
            }
        }
    }

    public final void replace (String column, Operator operator, Object value) {
        remove (column);
        add (column, operator, value);
    }

    public final void replace (String column, Object value) {
        replace (column, Operator.EQ, value);
    }

    public final int size () {
        return list.size ();
    }

    private static class EmptyDataFilter extends DataFilter {
        private EmptyDataFilter () {
            super (Collections.EMPTY_LIST);
        }

        public void add (String column, Operator operator, Object value) {
            throw new UnsupportedOperationException ();
        }
    }
}
