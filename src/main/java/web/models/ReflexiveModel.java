package web.models;

import org.apache.wicket.model.IModel;

public abstract class ReflexiveModel implements IModel <ReflexiveModel> {
    protected ReflexiveModel () {
    }

    public void detach () {}

    public ReflexiveModel getObject () {
        return this;
    }

    public void setObject (ReflexiveModel object) {}
}
