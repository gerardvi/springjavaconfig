package commons.config.jndi;

import javax.sql.DataSource;

import commons.config.IDatabaseVariables;

public abstract class JndiDatabaseVariables implements IDatabaseVariables {
    private final DataSource dataSource;
    private final String hibernateDialect;
    private final String hibernateFormatSql;
    private final String hibernateUseSqlComments;
    private final Boolean hibernateShowSql;

    protected JndiDatabaseVariables () {
        JndiDataSourceSupply dssupply = new JndiDataSourceSupply ();
        dataSource = dssupply.getDataSource ();
        hibernateDialect = dssupply.getHibernateDialect ();
        hibernateFormatSql = (String) Jndi.lookup ("hibernate_format_sql", Boolean.FALSE.toString ());
        hibernateUseSqlComments = (String) Jndi.lookup ("hibernate_use_sql_comments", Boolean.FALSE.toString ());
        hibernateShowSql = (Boolean) Jndi.lookup ("hibernate_show_sql", Boolean.FALSE);
    }

    @Override
    public DataSource getDataSource () {
        return dataSource;
    }

    @Override
    public String getHibernateDialect () {
        return hibernateDialect;
    }

    @Override
    public String getHibernateFormatSql () {
        return hibernateFormatSql;
    }

    @Override
    public boolean getHibernateShowSql () {
        return hibernateShowSql.booleanValue ();
    }

    @Override
    public String getHibernateUseSqlComments () {
        return hibernateUseSqlComments;
    }
}
