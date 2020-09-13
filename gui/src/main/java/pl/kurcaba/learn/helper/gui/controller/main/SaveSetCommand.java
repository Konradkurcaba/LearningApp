package pl.kurcaba.learn.helper.gui.controller.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;

import java.io.IOException;

public class SaveSetCommand extends MainWindowCommand
{

    private static final Logger logger = LogManager.getLogger(SaveSetCommand.class);

    public SaveSetCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        try {
            guiModelBroker.saveChanges();
        } catch (IOException aEx) {
            logger.error("A problem has occurred:", aEx);
        }
    }

    @Override
    public boolean canBeExecuted() {
        return guiModelBroker.getUnsavedChangesProperty().get();
    }
}
