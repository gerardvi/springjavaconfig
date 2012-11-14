package commons;

@Deprecated
public final class Beans {
    private Beans () {}

    /** Note that beans have a default constructor. */
    public static final Object newInstance (Class beanType) {
        Object bean;
        try {
            bean = beanType.newInstance ();
        } catch (Exception x) {
            throw new RuntimeException (x);
        }
        return bean;
    }
}
