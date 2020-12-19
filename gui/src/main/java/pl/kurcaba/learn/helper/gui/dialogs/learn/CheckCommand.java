package pl.kurcaba.learn.helper.gui.dialogs.learn;

import pl.kurcaba.learn.helper.gui.dialogs.options.LearnOptions;

public class CheckCommand extends AbstractLearnPanelCmd
{

    public CheckCommand(LearnPanelController controller) {
        super(controller);
    }

    @Override
    public void executeCommand() {
        {
            LearnOptions options = getWindowController().getLearnOptions();
            if (!options.isDefinitionShown()) {
                String typedDefinition = getWindowController().getDefinitionTextFieldValue();
                if (typedDefinition.equals(getWindowController().getCurrentCaseView().getDefinition())) {
                    getWindowController().removePseudoClassFromDefinitionTf(LearnPanelController.INCORRECT_CSS_CLASS);
                    getWindowController().addPseudoClassToDefinitionTf(LearnPanelController.CORRECT_CSS_CLASS);
                } else {
                    getWindowController().removePseudoClassFromDefinitionTf(LearnPanelController.CORRECT_CSS_CLASS);
                    getWindowController().addPseudoClassToDefinitionTf(LearnPanelController.INCORRECT_CSS_CLASS);
                }
            }
            if (!options.isNameShown()) {
                String typedName = getWindowController().getNameTextFieldValue();
                if (typedName.equals(getWindowController().getCurrentCaseView().getName())) {
                    getWindowController().removePseudoClassFromNameTf(LearnPanelController.INCORRECT_CSS_CLASS);
                    getWindowController().addPseudoClassToNameTf(LearnPanelController.CORRECT_CSS_CLASS);
                } else {
                    getWindowController().removePseudoClassFromNameTf(LearnPanelController.CORRECT_CSS_CLASS);
                    getWindowController().addPseudoClassToNameTf(LearnPanelController.INCORRECT_CSS_CLASS);
                }
            }
        }
    }

    @Override
    public boolean canBeExecuted() {
        return true;
    }
}
