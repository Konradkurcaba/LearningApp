package pl.kurcaba.learn.helper.gui.controller.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

import java.io.IOException;
import java.util.Optional;


public class LearnSetListFocusedCommand extends MainWindowCommand
{

    private static final Logger logger = LogManager.getLogger(LearnSetListFocusedCommand.class);

    public LearnSetListFocusedCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        Optional<LearnSetName> focusedName = windowController.getFocusedLearnSet();
        if(focusedName.isPresent()) {
            try {
                guiModelBroker.changeCurrentSet(focusedName.get());
                windowController.refreshSetData();
            } catch (IOException | ClassNotFoundException aEx) {
                logger.error(aEx);
            }
        }
    }
}
