package pl.kurcaba.learn.helper.gui.controller.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.core.ApplicationConstants;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.learnset.values.LearnSetNameFormatException;
import pl.kurcaba.learn.helper.learnset.values.NonUniqueException;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateSetCommand extends MainWindowCommand
{

    private static final Logger logger = LogManager.getLogger(CreateSetCommand.class);

    public CreateSetCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        ResourceBundle textBundle = ResourceBundle.getBundle(ApplicationConstants.DEFAULT_BUNDLE_NAME);
        Optional<String> newSetName = windowController
                .displayTextInputDialog(textBundle.getString("newSetName"));
        if(newSetName.isPresent())
        {
            try
            {
                guiModelBroker.createNewLearnSet(new LearnSetName(newSetName.get()));
                windowController.refreshMainListData();
            }
            catch (NonUniqueException aEx)
            {
                windowController.displayConfirmDialog(textBundle.getString("SetIsNotUnique"));
            }
            catch (LearnSetNameFormatException aEx)
            {
                windowController.displayConfirmDialog(textBundle.getString("wrongLearnSetNameFormat"));
            }
            catch (IOException aEx)
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
