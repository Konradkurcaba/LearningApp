package pl.kurcaba.learn.helper.gui.dialogs.learn;

public class NextCaseCmd extends AbstractLearnCmd {

    public NextCaseCmd(LearnPanelController controller) {
        super(controller);
    }

    @Override
    public void executeCommand()
    {
        if(!canBeExecuted())
        {
            throw new IllegalStateException("Cannot display next case, there is no next case");
        }

        controller.displayCase(controller.learnCases.get(controller.getCurrentCaseIndex() + 1));
        controller.setCurrentCaseIndex(controller.getCurrentCaseIndex() + 1);
        controller.updateState();
        removePseudoStyleFromTf();
    }

    @Override
    public boolean canBeExecuted() {
        return controller.getCurrentCaseIndex() + 1 < controller.learnCases.size();
    }

}
