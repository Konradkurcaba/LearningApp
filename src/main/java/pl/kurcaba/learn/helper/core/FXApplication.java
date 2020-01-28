package pl.kurcaba.learn.helper.core;

import javafx.application.Application;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.screen.ScreenCapturer;

import java.util.Optional;

public class FXApplication extends Application
{
    @Override
    public void start(Stage aPrimaryStage) throws Exception
    {

        ScreenCapturer capturer = new ScreenCapturer();

        Optional<WritableImage> image = capturer.openScreenshotWindow();
        System.out.print("hehe");


    }
}
