package pl.kurcaba.learn.helper.gui.controller.main;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.dialogs.options.LearnOptions;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.util.List;
import java.util.Optional;

public class StartLearnCommand extends MainWindowCommand {

    public StartLearnCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        if (!canBeExecuted()) {
            throw new IllegalStateException("Command cannot be executed, any set is not selected");
        }
        Optional<LearnOptions> learnOptions = windowController.showLearnOptionsPanel();
        learnOptions.ifPresent(options -> {
            List<LearnCaseView> learnCases = guiModelBroker.getCaseViews();
            windowController.showLearnPanel(options, learnCases);
        });
    }

    @Override
    public boolean canBeExecuted() {
        return guiModelBroker.isLearnSetChosenProperty().get()
                && guiModelBroker.getCaseViews().size() > 0;
    }
}
