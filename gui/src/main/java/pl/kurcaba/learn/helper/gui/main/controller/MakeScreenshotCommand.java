package pl.kurcaba.learn.helper.gui.main.controller;

import javafx.scene.image.WritableImage;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.screen.ScreenCapturer;

import java.util.Optional;

public class MakeScreenshotCommand extends AbstractCommand{

    public MakeScreenshotCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        windowController.setMainWindowIconified(true);
        ScreenCapturer capturer = new ScreenCapturer();
        Optional<WritableImage> image = capturer.openScreenshotWindow();
        image.ifPresent(writableImage -> windowController.setLastScreenShot(writableImage));
        windowController.setMainWindowIconified(false);
    }
}
