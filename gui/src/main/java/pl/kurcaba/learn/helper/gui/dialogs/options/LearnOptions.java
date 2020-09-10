package pl.kurcaba.learn.helper.gui.dialogs.options;

public class LearnOptions
{

    private final boolean showName;
    private final boolean showDefinition;
    private final boolean showImage;

    public LearnOptions(boolean showName, boolean showDefinition, boolean showImage)
    {
        this.showName = showName;
        this.showDefinition = showDefinition;
        this.showImage = showImage;
    }

    public boolean isNameShown()
    {
        return showName;
    }

    public boolean isDefinitionShown()
    {
        return showDefinition;
    }

    public boolean isImageShown()
    {
        return showImage;
    }
}
