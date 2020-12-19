package pl.kurcaba.learn.helper.gui.dialogs.editcase;

import pl.kurcaba.learn.helper.gui.dialogs.addcase.AddCaseWindowController;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

public class EditCaseWindowController extends AddCaseWindowController
{
    void fillFields(LearnCaseView aCaseView)
    {
        nameTf.setText(aCaseView.getName());
        definitionTf.setText(aCaseView.getDefinition());
        if(!aCaseView.getImages().isEmpty())
        {
            this.screen = aCaseView.getImages().get(0);
//            ch.setSelected(true);
        }
    }
}
