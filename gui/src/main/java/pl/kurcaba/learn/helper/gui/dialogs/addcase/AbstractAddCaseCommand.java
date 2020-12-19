package pl.kurcaba.learn.helper.gui.dialogs.addcase;

import pl.kurcaba.learn.helper.gui.backend.AbstractCommand;

public class AbstractAddCaseCommand extends AbstractCommand<AddCaseWindowController>
{
    public AbstractAddCaseCommand(AddCaseWindowController aAddCaseController)
    {
        super(aAddCaseController);
    }

    @Override
    public void executeCommand()
    {
        getWindowController()
    }

    @Override
    public boolean canBeExecuted()
    {
        return false;
    }
}
