package pl.kurcaba.learn.helper.gui.dialogs.learn;

import pl.kurcaba.learn.helper.gui.dialogs.options.LearnOptions;

public class ShowCorrectAnswersCmd extends AbstractLearnCmd
{
    public ShowCorrectAnswersCmd(LearnPanelController controller)
    {
        super(controller);
    }

    @Override
    public void executeCommand()
    {
        LearnOptions options = controller.getLearnOptions();
        if(!options.isNameShown())
        {
            controller.showCorrectName();
        }
        if(!options.isDefinitionShown())
        {
            controller.showCorrectDefinition();
        }
        if(!options.isImageShown())
        {
            controller.showImage();
        }
    }

    @Override
    public boolean canBeExecuted()
    {
        return false;
    }
}
