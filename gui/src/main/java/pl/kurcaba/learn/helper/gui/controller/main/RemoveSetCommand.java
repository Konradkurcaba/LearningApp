package pl.kurcaba.learn.helper.gui.controller.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.core.ApplicationConstants;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;

import java.io.IOException;
import java.util.ResourceBundle;

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
        if (!canBeExecuted())
        {
            throw new IllegalStateException("Cannot delete a learn set if any set is not selected");
        }
        ResourceBundle textBundle = ResourceBundle.getBundle(ApplicationConstants.DEFAULT_BUNDLE_NAME);
        ConfirmationStatus status = getWindowController().displayConfirmDialog(textBundle.getString("removeSetConfirmationMessage"));

        if (status.equals(ConfirmationStatus.CONFIRMED))
        {
            LearnSetName learnSet = getWindowController().getDisplayedLearnSet().get();
            guiModelBroker.removeLearnSet(learnSet);
            getWindowController().refreshMainListData();
        }
    }

    @Override
    public boolean canBeExecuted()
    {
        return getWindowController().getDisplayedLearnSet().isPresent();
    }
}
