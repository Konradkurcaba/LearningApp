package pl.kurcaba.learn.helper.gui.controller.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

import java.io.IOException;
import java.util.Optional;


public class LearnSetFocusedCmd extends MainWindowCommand {

    private static final Logger logger = LogManager.getLogger(LearnSetFocusedCmd.class);

    public LearnSetFocusedCmd(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        Optional<LearnSetName> focusedName = windowController.getFocusedLearnSet();
        if (focusedName.isPresent()) {
            try {
                boolean isNewNameFocused = isNewLearnSetFocused(focusedName);
                if (isNewNameFocused) {
                    if(canChangeLearnSet())
                    {
                        changeCurrentLearnSet(focusedName.get());
                    }
                    else
                    {
                        revertFocus();
                    }
                }

            } catch (IOException | ClassNotFoundException aEx) {
                logger.error(aEx);
            }
        }
    }

    @Override
    public boolean canBeExecuted() {
        return true;
    }

    private boolean isNewLearnSetFocused(Optional<LearnSetName> focusedName) {
        boolean isNewNameFocused;
        if (windowController.getDisplayedLearnSet().isPresent()) {
            isNewNameFocused = !focusedName.get().equals(windowController.getDisplayedLearnSet()
                    .get());
        } else {
            isNewNameFocused = true;
        }
        return isNewNameFocused;
    }

    private void changeCurrentLearnSet(LearnSetName focusedName) throws IOException, ClassNotFoundException {
        guiModelBroker.changeCurrentSet(focusedName);
        windowController.setDisplayedLearnSet(focusedName);
        windowController.refreshSetData();
    }

}

