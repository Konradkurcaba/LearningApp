package pl.kurcaba.learn.helper.gui.dialogs.learn;

public class ClearTfPseudoClasses extends AbstractLearnPanelCmd
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
