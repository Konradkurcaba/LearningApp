package pl.kurcaba.learn.helper.gui.dialogs.addcase;

import pl.kurcaba.learn.helper.gui.backend.AbstractCommand;

public class RemoveScreenshotCmd extends AbstractCommand<AddCaseWindowController>
{
    public RemoveScreenshotCmd(AddCaseWindowController windowController)
    {
        super(windowController);
    }

    @Override
    public void executeCommand()
    {
        getWindowController().removeFocusedScreenshot();
    }

    @Override
    public boolean canBeExecuted()
    {
        return getWindowController().isAnyScreenshotSelected();
    }
}
