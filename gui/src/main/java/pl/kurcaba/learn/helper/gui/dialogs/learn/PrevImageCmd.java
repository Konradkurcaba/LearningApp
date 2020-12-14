package pl.kurcaba.learn.helper.gui.dialogs.learn;

public class PrevImageCmd extends AbstractLearnCmd
{
    public PrevImageCmd(LearnPanelController controller)
    {
        super(controller);
    }

    @Override
    public void executeCommand()
    {
        controller.displayPrevImage();
    }

    @Override
    public boolean canBeExecuted()
    {
        return controller.canShowPrevImage();
    }
}
