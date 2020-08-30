package pl.kurcaba.learn.helper.gui.controller.main;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.util.List;

public class StartLearnCommand extends MainWindowCommand {

    public StartLearnCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        if (!canBeExecuted()) {
            throw new IllegalStateException("Command cannot be executed, any set is not selected");
        }
        List<LearnCaseView> learnCases = guiModelBroker.getCaseViews();
        windowController.showLearnPanel(true, true
                , true, learnCases);
    }

    @Override
    public boolean canBeExecuted() {
        return guiModelBroker.isLearnSetChosenProperty().get()
                && guiModelBroker.getCaseViews().size() > 0;
    }
}
