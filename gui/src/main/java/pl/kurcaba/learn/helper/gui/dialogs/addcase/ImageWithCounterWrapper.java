package pl.kurcaba.learn.helper.gui.dialogs.addcase;

import javafx.scene.image.WritableImage;

public class ImageWithCounterWrapper
{

    private final WritableImage aImage;
    private final int imageNumber;

    public ImageWithCounterWrapper(WritableImage aImage, int imageNumber)
    {
        this.aImage = aImage;
        this.imageNumber = imageNumber;
    }

    public WritableImage getImage()
    {
        return aImage;
    }

    public int getImageNumber()
    {
        return imageNumber;
    }
}
