package config;

import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import web.WicketApplication;

import commons.config.Springcontext;

@Configuration
@EnableTransactionManagement
@ComponentScan (basePackages = {"commons.config", "config", "dao", "services"})
public class Appcontext extends Springcontext {
    @Bean
    public WebApplication webApplication () {
        return new WicketApplication ();
    }
}
