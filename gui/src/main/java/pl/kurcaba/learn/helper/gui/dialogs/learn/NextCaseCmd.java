package pl.kurcaba.learn.helper.gui.dialogs.learn;

public class NextCaseCmd extends AbstractLearnPanelCmd
{

    public NextCaseCmd(LearnPanelController controller)
    {
        super(controller);
    }

    @Override
    public void executeCommand()
    {
        if (!canBeExecuted())
        {
            throw new IllegalStateException("Cannot display next case, there is no next case");
        }

        getWindowController().displayCase(getWindowController().learnCases.get(getWindowController().getCurrentCaseIndex() + 1));
        getWindowController().setCurrentCaseIndex(getWindowController().getCurrentCaseIndex() + 1);
        getWindowController().updateState();
        removePseudoStyleFromTf();
    }

    @Override
    public boolean canBeExecuted()
    {
        return getWindowController().getCurrentCaseIndex() + 1 < getWindowController().learnCases.size();
    }

}
