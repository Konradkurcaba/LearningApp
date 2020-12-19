package pl.kurcaba.learn.helper.gui.dialogs.learn;

public class NextImageCmd extends AbstractLearnPanelCmd
{
    public NextImageCmd(LearnPanelController controller)
    {
        super(controller);
    }

    @Override
    public void executeCommand()
    {
        getWindowController().displayNextImage();
        getWindowController().updateState();
    }

    @Override
    public boolean canBeExecuted()
    {
        return getWindowController().canShowNextImage();
    }
}
