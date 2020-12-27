package pl.kurcaba.learn.helper.gui.dialogs.options;

public class LearnOptions
{

    private final boolean showName;
    private final boolean showDefinition;
    private final boolean showImage;
    private final boolean randomOrder;

    public LearnOptions(boolean showName, boolean showDefinition, boolean showImage, boolean aRandomOrder)
    {
        this.showName = showName;
        this.showDefinition = showDefinition;
        this.showImage = showImage;
        this.randomOrder = aRandomOrder;
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

    public boolean isRandomOrder()
    {
        return randomOrder;
    }
}
