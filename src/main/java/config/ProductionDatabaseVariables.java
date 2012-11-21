package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import commons.config.jndi.JndiDatabaseVariables;

@Configuration
@Profile ("production")
public class ProductionDatabaseVariables extends JndiDatabaseVariables {
    public ProductionDatabaseVariables () {
    }
}
