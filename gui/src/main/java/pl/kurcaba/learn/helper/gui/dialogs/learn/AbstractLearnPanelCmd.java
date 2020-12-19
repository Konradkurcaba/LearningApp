package pl.kurcaba.learn.helper.gui.dialogs.learn;

import javafx.beans.property.SimpleBooleanProperty;
import pl.kurcaba.learn.helper.gui.backend.AbstractCommand;

public abstract class AbstractLearnPanelCmd extends AbstractCommand<LearnPanelController>
{

    private final SimpleBooleanProperty canBePerformed;

    public AbstractLearnPanelCmd(LearnPanelController aWindowController)
    {
        super(aWindowController);
        canBePerformed = new SimpleBooleanProperty(canBeExecuted());
    }

    protected void removePseudoStyleFromTf()
    {
        getWindowController().removePseudoClassFromDefinitionTf(LearnPanelController.CORRECT_CSS_CLASS);
        getWindowController().removePseudoClassFromDefinitionTf(LearnPanelController.INCORRECT_CSS_CLASS);
        getWindowController().removePseudoClassFromNameTf(LearnPanelController.CORRECT_CSS_CLASS);
        getWindowController().removePseudoClassFromNameTf(LearnPanelController.INCORRECT_CSS_CLASS);
    }

    public SimpleBooleanProperty canBePerformedProperty()
    {
        return canBePerformed;
    }
}
