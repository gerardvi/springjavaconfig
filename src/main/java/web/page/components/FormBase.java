package web.page.components;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public abstract class FormBase <T> extends Form <T> {
    protected FormBase (String wicketId, IModel <T> ioModel) {
        super (wicketId, ioModel);
    }

    protected FormBase (String wicketId) {
        this (wicketId, null);
    }

    protected FeedbackPanel addFeedback () {
        FeedbackPanel addedComponent = new FeedbackPanel ("feedback");
        add (addedComponent);
        return addedComponent;
    }

    /**
     * The specified <tt>wicketId</tt> will also serve as property name
     * (of a property of the io-model of this form).
     */
    protected TextField addTextField (String wicketId) {
        TextField addedComponent = new TextField (wicketId, new PropertyModel (getModel (), wicketId));
        add (addedComponent);
        return addedComponent;
    }
}
