package pl.kurcaba.learn.helper.gui.dialogs.learn;

public class PrevCaseCmd extends AbstractLearnPanelCmd
{

    public PrevCaseCmd(LearnPanelController controller) {
        super(controller);
    }

    @Override
    public void executeCommand() {
        if(!canBeExecuted())
        {
            throw new IllegalStateException("Cannot display a next case, there is no the next case");
        }

        getWindowController().displayCase(getWindowController().learnCases.get(getWindowController().getCurrentCaseIndex() - 1));
        getWindowController().setCurrentCaseIndex(getWindowController().getCurrentCaseIndex() - 1);
        getWindowController().updateState();
        removePseudoStyleFromTf();
        getWindowController().updateState();
    }

    @Override
    public boolean canBeExecuted() {
        return getWindowController().getCurrentCaseIndex() > 0;
    }

}
