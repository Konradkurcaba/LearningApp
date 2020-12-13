package pl.kurcaba.learn.helper.gui.controller.main;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;

/**
 * Enables to invoke async command and define what should happen after this command.
 *
 * @param <T>
 */
public abstract class AbstractAsyncCommand<T> extends Service<T>
{

    private final GuiModelBroker modelBroker;
    private final MainWindowController mainWindowController;

    public AbstractAsyncCommand(GuiModelBroker modelBroker, MainWindowController mainWindowController)
    {
        this.modelBroker = modelBroker;
        this.mainWindowController = mainWindowController;
    }

    @Override
    protected Task<T> createTask()
    {
        initAfterTaskListeners();
        return new Task<T>()
        {
            @Override
            protected T call() throws Exception
            {
                return asyncTask();
            }
        };
    }

    /**
     * Task which is invoked on a other thread.
     *
     * @return generic data
     */
    protected abstract T asyncTask();

    /**
     * This method enables to define what should happen after task is finished.
     * Standard listeners from {@link Service} should be used.
     */
    protected abstract void initAfterTaskListeners();

    public GuiModelBroker getModelBroker()
    {
        return modelBroker;
    }

    public MainWindowController getMainWindowController()
    {
        return mainWindowController;
    }
}
