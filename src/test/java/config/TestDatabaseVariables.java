package config;

import javax.sql.DataSource;

import org.hibernate.dialect.HSQLDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import commons.config.IDatabaseVariables;

@Configuration
@Profile ("test")
public class TestDatabaseVariables implements IDatabaseVariables {
    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSource dataSource () {
        return new EmbeddedDatabaseBuilder ().setName ("tp").addScript ("file:environments/hsqldb/init.sql").build ();
    }

    @Override
    public DataSource getDataSource () {
        return dataSource;
    }

    @Override
    public String getHibernateDialect () {
        return HSQLDialect.class.getName ();
    }

    @Override
    public String getHibernateFormatSql () {
        return Boolean.FALSE.toString ();
    }

    @Override
    public boolean getHibernateShowSql () {
        return true;
    }

    @Override
    public String getHibernateUseSqlComments () {
        return Boolean.TRUE.toString ();
    }
}
