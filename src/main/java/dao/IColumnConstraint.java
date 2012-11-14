package dao;

public interface IColumnConstraint {
    String getColumn ();
    Operator getOperator ();
    Object getValue ();

    public static final class Operator {
        public static final Operator EQ = new Operator ("=:");
        public static final Operator NEQ = new Operator ("!=:");
        /* TODO: add more operators. */

        private final String strval;

        private Operator (String strval) {
            this.strval = strval;
        }

        public String toString () {
            return strval;
        }
    }
}
