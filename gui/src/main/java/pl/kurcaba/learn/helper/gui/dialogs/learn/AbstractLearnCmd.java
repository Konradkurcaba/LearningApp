package pl.kurcaba.learn.helper.gui.dialogs.learn;

import javafx.beans.property.SimpleBooleanProperty;
import pl.kurcaba.learn.helper.gui.controller.main.CommandIf;

public abstract class AbstractLearnCmd implements CommandIf {

    protected final LearnPanelController controller;
    private final SimpleBooleanProperty canBePerformed;

    public AbstractLearnCmd(LearnPanelController controller)
    {
        this.controller = controller;
        canBePerformed = new SimpleBooleanProperty(canBeExecuted());
    }

    protected void removePseudoStyleFromTf()
    {
        controller.removePseudoClassFromDefinitionTf(LearnPanelController.CORRECT_CSS_CLASS);
        controller.removePseudoClassFromDefinitionTf(LearnPanelController.INCORRECT_CSS_CLASS);
        controller.removePseudoClassFromNameTf(LearnPanelController.CORRECT_CSS_CLASS);
        controller.removePseudoClassFromNameTf(LearnPanelController.INCORRECT_CSS_CLASS);
    }

    public SimpleBooleanProperty canBePerformedProperty()
    {
        return canBePerformed;
    }
}
