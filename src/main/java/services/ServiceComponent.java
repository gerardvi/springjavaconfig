package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import commons.Entity;

import dao.DaoComponent;
import dao.DataFilter;

@Component
public class ServiceComponent {
    @Autowired
    private DaoComponent daoComponent;

    public long count (Class type) {
        return count (type, DataFilter.EMPTY_DATA_FILTER);
    }

    public long count (Class type, DataFilter dataFilter) {
        return daoComponent.count (type, dataFilter);
    }

    public void delete (Class type, long id) {
        daoComponent.delete (type, id);
    }

    public Entity read (Class type, long id) {
        return daoComponent.read (type, id);
    }

    public List <Entity> read (Class type, DataFilter dataFilter, int fromIndex, int max, String sortProp, boolean asc) {
        return daoComponent.read (type, dataFilter, fromIndex, max, sortProp, asc);
    }

    public void write (Entity entity) {
        daoComponent.write (entity);
    }
}
