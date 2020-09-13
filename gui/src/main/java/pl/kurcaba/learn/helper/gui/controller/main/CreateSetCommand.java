package pl.kurcaba.learn.helper.gui.controller.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.learnset.values.NonUniqueException;

import java.io.IOException;
import java.util.Optional;

public class CreateSetCommand extends MainWindowCommand
{

    private static final Logger logger = LogManager.getLogger(CreateSetCommand.class);

    public CreateSetCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        Optional<String> newSetName = windowController
                .displayTextInputDialog("Nazwa nowego zestawu:");
        if(newSetName.isPresent())
        {
            try
            {
                guiModelBroker.createNewLearnSet(new LearnSetName(newSetName.get()));
                windowController.refreshMainListData();
            } catch (IOException | NonUniqueException aEx)
            {
                logger.error("A problem has occurred:", aEx);
            }
        }
    }

    @Override
    public boolean canBeExecuted() {
        return true;
    }
}
