package pl.kurcaba.learn.helper.gui.dialogs.learn;

public class ClearTfPseudoClasses extends AbstractLearnCmd
{

    public ClearTfPseudoClasses(LearnPanelController controller) {
        super(controller);
    }

    @Override
    public void executeCommand() {
        removePseudoStyleFromTf();
    }

    @Override
    public boolean canBeExecuted() {
        return true;
    }
}
