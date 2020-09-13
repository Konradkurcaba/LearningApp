package pl.kurcaba.learn.helper.gui.controller.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

import java.io.IOException;

public class RemoveSetCommand extends MainWindowCommand
{

    private static final Logger logger = LogManager.getLogger(RemoveSetCommand.class);

    public RemoveSetCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController)
    {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand()
    {
        if(!canBeExecuted())
        {
            throw new IllegalStateException("Cannot delete a learn set if any set is not selected");
        }
        ConfirmationStatus status = windowController.displayConfirmDialog("Czy chcesz usunąć zestaw?\n" +
                " Tej operacji nie można cofnąć.");

        if(status.equals(ConfirmationStatus.CONFIRMED))
        {
            try {
                LearnSetName learnSet = windowController.getDisplayedLearnSet().get();
                guiModelBroker.removeLearnSet(learnSet);
                windowController.refreshMainListData();
            } catch (IOException aException) {
                logger.error("A problem has occurred:", aException);
            }
        }
    }

    @Override
    public boolean canBeExecuted()
    {
        return windowController.getDisplayedLearnSet().isPresent();
    }
}
