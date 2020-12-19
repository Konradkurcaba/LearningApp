package pl.kurcaba.learn.helper.gui.dialogs.learn;

import pl.kurcaba.learn.helper.gui.dialogs.options.LearnOptions;

public class ShowCorrectAnswersCmd extends AbstractLearnPanelCmd
{
    public ShowCorrectAnswersCmd(LearnPanelController controller)
    {
        super(controller);
    }

    @Override
    public void executeCommand()
    {
        LearnOptions options = getWindowController().getLearnOptions();
        if(!options.isNameShown())
        {
            getWindowController().showCorrectName();
        }
        if(!options.isDefinitionShown())
        {
            getWindowController().showCorrectDefinition();
        }
        boolean hasAnyImages = !getWindowController().getCurrentCaseView().getImages().isEmpty();
        if(!options.isImageShown() && hasAnyImages)
        {
            getWindowController().showImage(0);
        }
    }

    @Override
    public boolean canBeExecuted()
    {
        return false;
    }
}
