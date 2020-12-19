package pl.kurcaba.learn.helper.gui.controller;

import pl.kurcaba.learn.helper.gui.backend.AbstractCommand;

public class CloseWindowCommand extends AbstractCommand<AbstractWindowController>
{
    public CloseWindowCommand(AbstractWindowController windowController)
    {
        super(windowController);
    }

    @Override
    public void executeCommand()
    {
        getWindowController().killThisWindow();
    }

    @Override
    public boolean canBeExecuted() {
        return true;
    }
}
