package pl.kurcaba.learn.helper.gui.controller.screenshot;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowDisplayer;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;

public class ConfirmImageDialog extends AbstractWindowDisplayer<ScreenshotPreviewController> {

    private static final Logger logger = LogManager.getLogger(ConfirmationStatus.class);
    public static final String CONFIRM_IMAGE_FXML = "fxml/screen_preview_panel.fxml";
    private Image image;

    public ConfirmationStatus showDialog(Image aImageToShow)
    {
        image = aImageToShow;
        Stage stage = prepareStage(CONFIRM_IMAGE_FXML);
        stage.showAndWait();
        return getController().getStatus();
    }

    @Override
    protected ScreenshotPreviewController createController() {
        return new ScreenshotPreviewController(image);
    }
}
