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

    public boolean isShowName()
    {
        return showName;
    }

    public boolean isShowDefinition()
    {
        return showDefinition;
    }

    public boolean isShowImage()
    {
        return showImage;
    }
}
