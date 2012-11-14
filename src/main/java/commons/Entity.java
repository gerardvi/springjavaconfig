package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Entity {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private long id;

    protected Entity () {
    }

    public long getId () {
        return id;
    }

    public boolean isNew () {
        return id == 0;
    }

    /**
     * Validates that one can assign <tt>Entity</tt> from the specified <tt>type</tt>.
     * This function is intended to be used in situations where one wishes to validate
     * a specified argument of type <tt>Class</tt>: <tt>argName</tt> is its name and
     * will be used to construct an error message in case validation fails.
     */
    public static void validate (Class type, String argName) {
        if (type == null  ||  ! Entity.class.isAssignableFrom (type)) {
            throw new IllegalArgumentException ("The specified argument `".concat (argName)
                                                                          .concat ("' should be a type extending ")
                                                                          .concat (Entity.class.getName ())
                                                                          .concat ("'."));
        }
    }
}
