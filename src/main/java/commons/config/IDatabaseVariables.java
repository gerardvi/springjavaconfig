package commons.config;

import javax.sql.DataSource;

public interface IDatabaseVariables {
    DataSource getDataSource ();
    String getHibernateDialect ();
    String getHibernateFormatSql ();
    boolean getHibernateShowSql ();
    String getHibernateUseSqlComments ();
}
