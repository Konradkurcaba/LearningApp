package pl.kurcaba.learn.helper.gui.controller.main;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;

public class MinimizeWindowCommand extends MainWindowCommand
{
    public MinimizeWindowCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController)
    {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand()
    {
        windowController.setWindowIconified(true);
    }

    @Override
    public boolean canBeExecuted() {
        return true;
    }
}
