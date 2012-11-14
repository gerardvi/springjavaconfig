package web;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import web.page.WordListPage;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see web.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
    public static final int GUI_LIST_SIZE = 16;

    public Class getHomePage () {
        return WordListPage.class;
    }

    public void init () {
        super.init ();

        /* Injector.get () shouldn't return null, we want to use @SpringBean: */
        getComponentInstantiationListeners ().add (new SpringComponentInjector (this));
    }
}
