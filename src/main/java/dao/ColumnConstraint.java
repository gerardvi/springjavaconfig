package dao;

public class ColumnConstraint implements IColumnConstraint {
    private final String column;
    private final Operator operator;
    private final Object value;

    ColumnConstraint (String column, Operator operator, Object value) {
        if (column==null || operator==null || value==null) {
            throw new IllegalArgumentException ("Neither of the arguments should be null.");
        }
        this.operator = operator;
        this.column = column;
        this.value = value;
    }

    @Override
    public String getColumn () {
        return column;
    }

    @Override
    public Operator getOperator () {
        return operator;
    }

    @Override
    public Object getValue () {
        return value;
    }
}
