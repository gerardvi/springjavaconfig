package web.page.components;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

public abstract class DataViewBase extends DataView {
    protected DataViewBase (String wicketId, IDataProvider dataProvider, int itemsPerPage) {
        super (wicketId, dataProvider, itemsPerPage);
    }

    protected Item newItem (String wicketId, int index, IModel model) {
        return new CustomisedItem (wicketId, index, model);
    }

    private static class CustomisedItem extends Item {
        CustomisedItem (String wicketId, int index, IModel model) {
            super (wicketId, index, model);
        }

        protected void onComponentTag (ComponentTag tag) {
            super.onComponentTag (tag);
            tag.put ("class", (getIndex () & 1) == 0 ? "even" : "odd");
        }
    }
}
