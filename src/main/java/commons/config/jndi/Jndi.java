package commons.config.jndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public final class Jndi {
    private Jndi () {}

    private static final Context CONTEXT;

    static {
        try {
            CONTEXT = (Context) new InitialContext ().lookup ("java:comp/env");
        } catch (NamingException x) {
            throw new RuntimeException (x);
        }
    }

    public static Object lookup (String key) {
        Object value;
        try {
            value = CONTEXT.lookup (key);
        } catch (NamingException x) {
            value = null;
        }
        return value;
    }

    public static Object lookup (String key, Object defaultValue) {
        Object value = lookup (key);
        return value != null ? value : defaultValue;
    }

    public static Object lookupNotNull (String key) {
        Object value = lookup (key);
        if (value == null) {
            throw new RuntimeException ("No Jndi-entry defined for `".concat (key).concat ("'."));
        }
        return value;
    }
}
