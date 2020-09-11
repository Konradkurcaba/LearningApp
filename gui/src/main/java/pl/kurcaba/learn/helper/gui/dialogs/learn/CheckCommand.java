package pl.kurcaba.learn.helper.gui.dialogs.learn;

import pl.kurcaba.learn.helper.gui.dialogs.options.LearnOptions;

public class CheckCommand extends AbstractLearnCmd {

    public CheckCommand(LearnPanelController controller) {
        super(controller);
    }

    @Override
    public void executeCommand() {
        {
            LearnOptions options = controller.getLearnOptions();
            if (!options.isDefinitionShown()) {
                String typedDefinition = controller.getDefinitionTextFieldValue();
                if (typedDefinition.equals(controller.getCurrentCaseView().getDefinition())) {
                    controller.removePseudoClassFromDefinitionTf(LearnPanelController.INCORRECT_CSS_CLASS);
                    controller.addPseudoClassToDefinitionTf(LearnPanelController.CORRECT_CSS_CLASS);
                } else {
                    controller.removePseudoClassFromDefinitionTf(LearnPanelController.CORRECT_CSS_CLASS);
                    controller.addPseudoClassToDefinitionTf(LearnPanelController.INCORRECT_CSS_CLASS);
                }
            }
            if (!options.isNameShown()) {
                String typedName = controller.getNameTextFieldValue();
                if (typedName.equals(controller.getCurrentCaseView().getName())) {
                    controller.removePseudoClassFromNameTf(LearnPanelController.INCORRECT_CSS_CLASS);
                    controller.addPseudoClassToNameTf(LearnPanelController.CORRECT_CSS_CLASS);
                } else {
                    controller.removePseudoClassFromNameTf(LearnPanelController.CORRECT_CSS_CLASS);
                    controller.addPseudoClassToNameTf(LearnPanelController.INCORRECT_CSS_CLASS);
                }
            }
        }
    }

    @Override
    public boolean canBeExecuted() {
        return true;
    }
}
