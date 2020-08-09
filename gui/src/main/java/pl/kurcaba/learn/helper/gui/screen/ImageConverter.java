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

    public static byte[] convertToByte(WritableImage aImage) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(aImage, null);
        ImageIO.write(bufferedImage, "png", byteStream);

        return byteStream.toByteArray();
    }

    public static WritableImage convertToImage(byte[] aBytes) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(aBytes);
        BufferedImage readImage = ImageIO.read(byteStream);
        return SwingFXUtils.toFXImage(readImage, null);
    }


}
