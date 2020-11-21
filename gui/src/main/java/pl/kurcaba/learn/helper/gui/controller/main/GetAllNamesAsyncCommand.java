package pl.kurcaba.learn.helper.gui.controller.main;

import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;

import java.io.IOException;
import java.util.List;

public class GetAllNamesAsyncCommand extends AbstractAsyncCommand<List<LearnSetName>>
{
    public GetAllNamesAsyncCommand(GuiModelBroker modelBroker, MainWindowController mainWindowController)
    {
        super(modelBroker, mainWindowController);
    }

    @Override
    protected List<LearnSetName> asyncTask()
    {
        try
        {
            return getModelBroker().getAllSetsNames();
        } catch (IOException aEx)
        {
            failed();
            return null;
        }
    }

    @Override
    protected void initAfterTaskListeners()
    {
        setOnSucceeded(event -> getMainWindowController().changeLearnSetNames(getValue()));
    }


}
