package pl.kurcaba.learn.helper.gui.main.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.learnset.values.NonUniqueException;

import java.io.IOException;

public class CreateSetCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(CreateSetCommand.class);

    public CreateSetCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        String newSetName = windowController.displayInputDialog("Nowy zestaw"
                , "Nazwa nowego zestawu:");

        try {
            guiModelBroker.createNewLearnSet(new LearnSetName(newSetName));
            windowController.refreshMainListData();
        } catch (IOException | NonUniqueException aEx) {
            logger.error(aEx);
        }
    }
}
