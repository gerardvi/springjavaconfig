package commons.config;

import org.hibernate.dialect.HSQLDialect;
import org.hibernate.dialect.PostgreSQLDialect;

import commons.Strings;

public enum DatabaseId {
    HSQL (HSQLDialect.class.getName ()),
    POSTGRES (PostgreSQLDialect.class.getName ());

    public static DatabaseId forName (String name) {
        if (Strings.isBlank (name)) {
            throw new IllegalArgumentException ("No `name' specified.");
        }
        try {
            return Enum.valueOf (DatabaseId.class, name);
        } catch (Exception x) {
            throw new IllegalArgumentException ("Unknown name: ".concat (name));
        }
    }

    private final String hibernateDialect;

    private DatabaseId (String hibernateDialect) {
        this.hibernateDialect = hibernateDialect;
    }

    public String getHibernateDialect () {
        return hibernateDialect;
    }
}
