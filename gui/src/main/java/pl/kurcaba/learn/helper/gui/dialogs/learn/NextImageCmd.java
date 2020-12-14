package pl.kurcaba.learn.helper.gui.dialogs.learn;

public class NextImageCmd extends AbstractLearnCmd
{
    public NextImageCmd(LearnPanelController controller)
    {
        super(controller);
    }

    @Override
    public void executeCommand()
    {
        controller.displayNextImage();
    }

    @Override
    public boolean canBeExecuted()
    {
        return controller.canShowNextImage();
    }
}
