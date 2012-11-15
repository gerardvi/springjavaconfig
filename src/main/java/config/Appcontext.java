package config;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import web.WicketApplication;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan (basePackages = {"dao", "services"})
public class Appcontext {
    @Autowired
    private IVariables variables;
    @Autowired
    private FactoryBean <EntityManagerFactory> factoryBean;

    @Bean
    public FactoryBean <EntityManagerFactory> entityManagerFactory () {
        Map <String, String> jpaPropertiesMap = new HashMap ();
        jpaPropertiesMap.put ("hibernate.hbm2ddl.auto", "validate");
        jpaPropertiesMap.put ("hibernate.dialect", variables.getHibernateDialect ());
        jpaPropertiesMap.put ("hibernate.format_sql", variables.getHibernateFormatSql ());
        jpaPropertiesMap.put ("hibernate.use_sql_comments", variables.getHibernateUseSqlComments ());

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter ();
        jpaVendorAdapter.setShowSql (variables.getHibernateShowSql ());

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean ();
        factoryBean.setJpaVendorAdapter (jpaVendorAdapter);
        factoryBean.setJpaPropertyMap (jpaPropertiesMap);
        factoryBean.setDataSource (variables.getDataSource ());
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager () {
        JpaTransactionManager transactionManager = new JpaTransactionManager ();
        transactionManager.setDataSource (variables.getDataSource ());
        try {
            transactionManager.setEntityManagerFactory (factoryBean.getObject ());
        } catch (Exception x) {
            throw new RuntimeException (x);
        }
        return transactionManager;
    }

    @Bean
    public WebApplication webApplication () {
        return new WicketApplication ();
    }

    public static interface IVariables {
        DataSource getDataSource ();
        String getHibernateDialect ();
        String getHibernateFormatSql ();
        boolean getHibernateShowSql ();
        String getHibernateUseSqlComments ();
    }

    @Configuration
    @Profile ("production")
    public static class ProductionVariables implements IVariables {
        @Autowired
        private DataSource dataSource;

        @Resource (mappedName = "hibernate_dialect")
        private String hibernateDialect;

        @Resource (mappedName = "hibernate_format_sql")
        private String hibernateFormatSql;

        @Resource (mappedName = "hibernate_use_sql_comments")
        private String hibernateUseSqlComments;

        @Resource (mappedName = "hibernate_show_sql")
        private Boolean hibernateShowSql;

        @Bean
        public DataSource dataSource () {
            ComboPooledDataSource ds = new ComboPooledDataSource ();
            try {
                ds.setDriverClass ("org.postgresql.Driver");
            } catch (PropertyVetoException x) {
                throw new RuntimeException (x);
            }
            ds.setJdbcUrl ("jdbc:postgresql://localhost/tp");
            ds.setUser ("tpowner");
            ds.setPassword ("tpowner");
            ds.setMinPoolSize (2);
            ds.setMaxPoolSize (20);
            ds.setMaxIdleTime (3600);
            ds.setIdleConnectionTestPeriod (300);
            ds.setAutomaticTestTable ("jdbc_pool_check");
            ds.setCheckoutTimeout (20000);
            return ds;
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

    @Configuration
    @Profile ("test")
    public static class TestVariables implements IVariables {
        @Autowired
        private DataSource dataSource;

        @Bean
        public DataSource dataSource () {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder ();
            EmbeddedDatabase ds = builder.setName ("dataSource").addScript ("file:environments/hsqldb/init.sql").build ();
            return ds;
        }

        @Override
        public DataSource getDataSource () {
            return dataSource;
        }

        @Override
        public String getHibernateDialect () {
            return "org.hibernate.dialect.HSQLDialect";
        }

        @Override
        public String getHibernateFormatSql () {
            return "false";
        }

        @Override
        public boolean getHibernateShowSql () {
            return true;
        }

        @Override
        public String getHibernateUseSqlComments () {
            return "true";
        }
    }
}
