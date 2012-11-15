package dao;

public interface IColumnConstraint {
    String getColumn ();
    Operator getOperator ();
    Object getValue ();

    public static final class Operator {
        public static final Operator EQ = new Operator ("=:");
        public static final Operator GEQ = new Operator (">=:");
        public static final Operator GR = new Operator (">:");
               static final Operator IS_NULL = new Operator (" is null");
        public static final Operator LE = new Operator ("<:");
        public static final Operator LEQ = new Operator ("<=:");
        public static final Operator NEQ = new Operator ("!=:");
        /* TODO: add operator LIKE */

        private final String strval;

        private Operator (String strval) {
            this.strval = strval;
        }

        public String toString () {
            return strval;
        }
    }
}
