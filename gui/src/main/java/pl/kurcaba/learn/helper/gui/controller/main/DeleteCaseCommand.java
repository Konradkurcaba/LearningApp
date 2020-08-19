package pl.kurcaba.learn.helper.gui.controller.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.util.Optional;

public class DeleteCaseCommand extends MainWindowCommand
{

    private static final Logger logger = LogManager.getLogger(DeleteCaseCommand.class);

    public DeleteCaseCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        Optional<LearnCaseView> view = windowController.getSelectedCaseView();
        if (view.isPresent()) {
            boolean status = guiModelBroker.deleteCase(view.get());
            if (status) {
                windowController.removeViewFromTable(view.get());
            }
        } else {
            logger.error("Cannot delete set, any set isn't selected");
        }
    }
}
