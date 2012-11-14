package web.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;

import web.WicketApplication;
import web.models.EntityModel;
import web.page.components.DataViewBase;
import web.page.components.EntityDataProvider;

import commons.Strings;

import domain.Word;

public class WordListPage extends WebPage {
    public WordListPage () {
        Form form = new Form ("form");
        EntityDataProvider dataProvider = new EntityDataProvider (Word.class);
        form.add (new DataView (dataProvider));
        form.add (new DetailsLink ());
        add (form);
    }

    private static class DataView extends DataViewBase {
        DataView (EntityDataProvider dataProvider) {
            super ("dataview", dataProvider, WicketApplication.GUI_LIST_SIZE);
        }

        @Override
        protected void populateItem (Item item) {
            Word entity = (Word) item.getModelObject ();
            item.add (new Label ("code", Strings.toString (entity.getCode ())));
            item.add (new Label ("type", Strings.toString (entity.getType ())));
            item.add (new DetailsLink (entity.getId ()));
        }
    }

    private static class DetailsLink extends Link {
        private long id;

        DetailsLink (long id) {
            super ("details");
            this.id = id;
        }

        DetailsLink () {
            this (0);
        }

        @Override
        public void onClick () {
            setResponsePage (new WordDetailsPage (new EntityModel (Word.class, id)));
        }
    }
}
