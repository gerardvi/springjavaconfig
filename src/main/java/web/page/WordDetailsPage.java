package web.page;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.spring.injection.annot.SpringBean;

import services.ServiceComponent;
import web.models.EntityModel;
import web.page.components.FormBase;
import domain.Word;
import domain.WordType;

public class WordDetailsPage extends WebPage {
    public WordDetailsPage (EntityModel wordModel) {
        add (new Form (wordModel));
    }

    private static class Form extends FormBase {
        Form (EntityModel wordModel) {
            super ("form", wordModel);
            Injector.get ().inject (this);

            addFeedback ();
            addTextField ("code");
            add (new SaveButton (this));
            add (new DeleteButton (this).setEnabled (wordModel.getId () != 0));
            add (new BackButton ());
        }
    }

    private static class BackButton extends Button {
        BackButton () {
            super ("back");
        }

        @Override
        public void onSubmit () {
            setResponsePage (WordListPage.class);
        }
    }

    private static class DeleteButton extends Button {
        @SpringBean
        private ServiceComponent service;

        private final Form form;

        DeleteButton (Form form) {
            super ("delete");
            this.form = form;
        }

        @Override
        public void onSubmit () {
            EntityModel model = (EntityModel) form.getModel ();
            service.delete (model.getEntityType (), model.getId ());
            setResponsePage (WordListPage.class);
        }
    }

    private static class SaveButton extends Button {
        @SpringBean
        private ServiceComponent service;

        private final Form form;

        SaveButton (Form form) {
            super ("save");
            this.form = form;
        }

        @Override
        public void onSubmit () {
            Word entity = (Word) form.getModelObject ();
            entity.setType (WordType.N); /* TODO: add dropdown to form. */
            /* TODO: remove try-catch and create nice error message. */
            try {
                service.write (entity);
                ((EntityModel) form.getModel ()).setId (entity.getId ());
                form.info ("Commit successful.");
            } catch (Exception x) {
                form.fatal ("Commit failed.");
            }
        }
    }
}
