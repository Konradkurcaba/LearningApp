package pl.kurcaba.learn.helper.gui.dialogs.learn;

import pl.kurcaba.learn.helper.gui.controller.main.CommandIf;

public abstract class AbstractLearnCmd implements CommandIf {

    protected final LearnPanelController controller;

    public AbstractLearnCmd(LearnPanelController controller) {
        this.controller = controller;
    }

    protected void removePseudoStyleFromTf()
    {
        controller.removePseudoClassFromDefinitionTf(LearnPanelController.CORRECT_CSS_CLASS);
        controller.removePseudoClassFromDefinitionTf(LearnPanelController.INCORRECT_CSS_CLASS);
        controller.removePseudoClassFromNameTf(LearnPanelController.CORRECT_CSS_CLASS);
        controller.removePseudoClassFromNameTf(LearnPanelController.INCORRECT_CSS_CLASS);
    }


}
