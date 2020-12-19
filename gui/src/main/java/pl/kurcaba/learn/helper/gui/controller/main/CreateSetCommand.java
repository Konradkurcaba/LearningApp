package pl.kurcaba.learn.helper.gui.controller.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.LearnSetNameFormatException;
import pl.kurcaba.learn.helper.common.values.NonUniqueException;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.core.ApplicationConstants;

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
        Optional<String> newSetName = getWindowController()
                .displayTextInputDialog(textBundle.getString("newSetName"));
        if(newSetName.isPresent())
        {
            try
            {
                guiModelBroker.createNewLearnSet(new LearnSetName(newSetName.get()));
                getWindowController().refreshMainListData();
            }
            catch (NonUniqueException aEx)
            {
                getWindowController().displayConfirmDialog(textBundle.getString("SetIsNotUnique"));
            }
            catch (LearnSetNameFormatException aEx)
            {
                getWindowController().displayConfirmDialog(textBundle.getString("wrongLearnSetNameFormat"));
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
