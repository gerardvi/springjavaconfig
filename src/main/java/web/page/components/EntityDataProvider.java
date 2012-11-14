package web.page.components;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import services.ServiceComponent;
import web.models.EntityModel;

import commons.Entity;

import dao.DataFilter;

public class EntityDataProvider implements IDataProvider <Entity>, ISortStateLocator {
    @SpringBean
    private ServiceComponent service;

    private final DataFilter dataFilter;
    private final SingleSortState sortState;
    private final Class type;

    public EntityDataProvider (Class type, String sortProperty, boolean ascending) {
        if (type == null  ||  sortProperty == null) {
            throw new IllegalArgumentException ("Both type and the name of the property to sort on need to be specified.");
        }
        Injector.get ().inject (this);
        this.type = type;
        this.dataFilter = new DataFilter ();
        this.sortState = new SingleSortState ();
        this.sortState.setPropertySortOrder (sortProperty, ascending ? SortOrder.ASCENDING : SortOrder.DESCENDING);
    }

    public EntityDataProvider (Class type, String sortProperty) {
        this (type, sortProperty, false);
    }

    public EntityDataProvider (Class type) {
        this (type, "id");
    }

    @Override
    public void detach () {
    }

    public DataFilter getDataFilter () {
        return dataFilter;
    }

    @Override
    public ISortState getSortState () {
        return sortState;
    }

    @Override
    public Iterator <Entity> iterator (int first, int count) {
        SortParam sp = sortState.getSort ();
        return service.read (type, dataFilter, first, count, sp.getProperty (), sp.isAscending ()).iterator ();
    }

    @Override
    public int size () {
        return (int) service.count (type, dataFilter);
    }

    @Override
    public IModel <Entity> model (Entity object) {
        return new EntityModel (object);
    }
}
