package pl.kurcaba.learn.helper.gui.dialogs.learn;

public class PrevImageCmd extends AbstractLearnPanelCmd
{
    public PrevImageCmd(LearnPanelController controller)
    {
        super(controller);
    }

    @Override
    public void executeCommand()
    {
        getWindowController().displayPrevImage();
        getWindowController().updateState();
    }

    @Override
    public boolean canBeExecuted()
    {
        return getWindowController().canShowPrevImage();
    }
}
