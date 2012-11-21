package commons.config.jndi;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.postgresql.Driver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import commons.Strings;
import commons.config.DatabaseConfigInfo;
import commons.config.DatabaseId;

/**
 * <p>
 * Constructs a <tt>javax.sql.DataSource</tt> and hibernate dialect information
 * from entries available through JNDI.
 * </p>
 * <tt>jetty-env.xml</tt> example for <tt>DatabaseId.POSTGRES</tt>:<pre>
 *&#x3C;?xml version="1.0" encoding="utf-8"?&#x3E;
 *&#x3C;Configure id="wapp" class="org.eclipse.jetty.webapp.WebAppContext"&#x3E;
 *  &#x3C;New class="org.eclipse.jetty.plus.jndi.Resource"&#x3E;
 *    &#x3C;Arg&#x3E;databaseConfigInfo&#x3C;/Arg&#x3E;
 *    &#x3C;Arg&#x3E;
 *      &#x3C;New class="commons.config.DatabaseConfigInfo"&#x3E;
 *        &#x3C;Set name="databaseId"&#x3E;POSTGRES&#x3C;/Set&#x3E;
 *        &#x3C;Set name="jdbcUrl"&#x3E;jdbc:postgresql://localhost/tp&#x3C;/Set&#x3E;
 *        &#x3C;Set name="user"&#x3E;tpowner&#x3C;/Set&#x3E;
 *        &#x3C;Set name="password"&#x3E;tpowner&#x3C;/Set&#x3E;
 *      &#x3C;/New&#x3E;
 *    &#x3C;/Arg&#x3E;
 *  &#x3C;/New&#x3E;
 *&#x3C;/Configure&#x3E;
 * </pre>
 * <tt>jetty-env.xml</tt> example for <tt>DatabaseId.HSQL</tt>:<pre>
 *&#x3C;?xml version="1.0" encoding="utf-8"?&#x3E;
 *&#x3C;Configure id="wapp" class="org.eclipse.jetty.webapp.WebAppContext"&#x3E;
 *  &#x3C;New class="org.eclipse.jetty.plus.jndi.Resource"&#x3E;
 *    &#x3C;Arg&#x3E;databaseConfigInfo&#x3C;/Arg&#x3E;
 *    &#x3C;Arg&#x3E;
 *      &#x3C;New class="commons.config.DatabaseConfigInfo"&#x3E;
 *        &#x3C;Set name="databaseId"&#x3E;HSQL&#x3C;/Set&#x3E;
 *        &#x3C;Set name="sqlInitFile"&#x3E;file:environments/hsqldb/init.sql&#x3C;/Set&#x3E;
 *      &#x3C;/New&#x3E;
 *    &#x3C;/Arg&#x3E;
 *  &#x3C;/New&#x3E;
 *&#x3C;/Configure&#x3E;
 * </pre>
 */
public final class JndiDataSourceSupply {
    private final DataSource dataSource;
    private final DatabaseId databaseId;

    public JndiDataSourceSupply () {
        DatabaseConfigInfo configInfo = (DatabaseConfigInfo) Jndi.lookupNotNull ("databaseConfigInfo");
        databaseId = DatabaseId.forName (configInfo.getDatabaseId ());
        switch (databaseId) {
        case HSQL: {
            String sqlInitFile = validateNonblankString (configInfo.getSqlInitFile (), "sqlInitFile");
            dataSource = new EmbeddedDatabaseBuilder ().setName ("testdb").addScript (sqlInitFile).build ();
        } break;

        case POSTGRES: {
            dataSource = newComboPooledDataSource (configInfo);
        } break;

        default: {
            throw new RuntimeException ();
        }
        }
    }

    public DataSource getDataSource () {
        return dataSource;
    }

    public String getHibernateDialect () {
        return databaseId.getHibernateDialect ();
    }

    private static DataSource newComboPooledDataSource (DatabaseConfigInfo configInfo) {
        ComboPooledDataSource ds = new ComboPooledDataSource ();
        try {
            ds.setDriverClass (Driver.class.getName ());
        } catch (PropertyVetoException x) {
            throw new RuntimeException (x);
        }
        ds.setJdbcUrl (validateNonblankString (configInfo.getJdbcUrl (), "jdbcUrl"));
        ds.setUser (validateNonblankString (configInfo.getUser (), "user"));
        ds.setPassword (validateNonblankString (configInfo.getPassword (), "password"));
        ds.setAutomaticTestTable (configInfo.getAutomaticTestTable ());
        ds.setMinPoolSize (configInfo.getMinPoolSize ());
        ds.setMaxPoolSize (configInfo.getMaxPoolSize ());
        ds.setMaxIdleTime (configInfo.getMaxIdleTime ());
        ds.setIdleConnectionTestPeriod (configInfo.getIdleConnectionTestPeriod ());
        ds.setCheckoutTimeout (configInfo.getCheckoutTimeout ());
        return ds;
    }

    private static String validateNonblankString (String str, String name) {
        if (Strings.isBlank (str)) {
            throw new IllegalStateException ("No `".concat (name).concat ("' specified."));
        }
        return str;
    }
}
