package pl.kurcaba.learn.helper.gui.concurrency;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.kurcaba.learn.helper.common.model.LearnSetDaoIf;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;

import java.util.List;

public class GetAllLearnSetNamesService extends Service<List<LearnSetName>>
{
    private final GuiModelBroker modelBroker;

    public GetAllLearnSetNamesService(GuiModelBroker broker)
    {
        modelBroker = broker;
    }

    @Override
    protected Task<List<LearnSetName>> createTask()
    {
        return new Task<>()
        {
            @Override
            protected List<LearnSetName> call() throws Exception
            {
                return modelBroker.getAllSetsNames();
            }
        };
    }
}
