package config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import commons.config.IDatabaseVariables;
import commons.web.Jndi;
import commons.web.JndiDataSourceSupply;

@Configuration
@Profile ("production")
public class ProductionDatabaseVariables implements IDatabaseVariables {
    private final DataSource dataSource;
    private final String hibernateDialect;
    private final String hibernateFormatSql;
    private final String hibernateUseSqlComments;
    private final Boolean hibernateShowSql;

    public ProductionDatabaseVariables () {
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
