package pl.kurcaba.learn.helper.gui.controller.main;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;

public class CloseMainWindowCommand extends MainWindowCommand {

    public CloseMainWindowCommand(GuiModelBroker aGuiModelBroker
            , MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        if(canBeExecuted())
        {
            windowController.closeApplication();
        }
    }

    @Override
    public boolean canBeExecuted() {
        return canChangeLearnSet();
    }
}
