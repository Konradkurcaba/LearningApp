package pl.kurcaba.learn.helper.gui.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

import java.io.IOException;


public class LearnSetListFocusedCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(LearnSetListFocusedCommand.class);

    public LearnSetListFocusedCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        LearnSetName focusedName = windowController.getFocusedLearnSet();
        try
        {
            guiModelBroker.changeCurrentSet(focusedName);
            windowController.refreshTableData();
        } catch (IOException | ClassNotFoundException aEx)
        {
           logger.error(aEx);
        }
    }
}
