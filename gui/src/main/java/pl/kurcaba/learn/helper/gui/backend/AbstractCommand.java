package pl.kurcaba.learn.helper.gui.backend;

public abstract class AbstractCommand<T> implements CommandIf
{

    private final T windowController;

    public AbstractCommand(T windowController)
    {
        this.windowController = windowController;
    }

    public T getWindowController()
    {
        return windowController;
    }
}
