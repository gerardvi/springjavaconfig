package config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import commons.config.IDatabaseVariables;

import web.WicketApplication;

@Configuration
@EnableTransactionManagement
@ComponentScan (basePackages = {"config", "dao", "services"})
public class Appcontext {
    @Autowired
    private IDatabaseVariables variables;
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
}
