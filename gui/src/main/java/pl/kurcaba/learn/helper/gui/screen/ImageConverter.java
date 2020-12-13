package pl.kurcaba.learn.helper.gui.screen;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageConverter {

    public static final Logger logger = LogManager.getLogger(ImageConverter.class);
    public static final String IMAGE_FORMAT = "png";

    public static byte[] convertToByte(WritableImage aImage)  {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(aImage, null);
        try
        {
            ImageIO.write(bufferedImage, IMAGE_FORMAT, byteStream);
        } catch (IOException e)
        {
            //we convert writableImage to byte output stream IN MEMORY, it is not a IO problem if something bad happen.
            throw new RuntimeException("Cannot convert an image to the byte array");
        }

        return byteStream.toByteArray();
    }

    public static WritableImage convertToImage(byte[] aBytes) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(aBytes);
        BufferedImage readImage = null;
        try
        {
            readImage = ImageIO.read(byteStream);
        } catch (IOException e)
        {
            //We convert images in memory, it is not a IO exception.
            throw new RuntimeException("Something went wrong during converting bytes to images");
        }
        return SwingFXUtils.toFXImage(readImage, null);
    }


}
