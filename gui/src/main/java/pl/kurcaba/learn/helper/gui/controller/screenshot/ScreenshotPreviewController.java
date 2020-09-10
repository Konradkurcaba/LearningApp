package pl.kurcaba.learn.helper.gui.controller.screenshot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowController;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;

public class ScreenshotPreviewController extends AbstractWindowController {

    @FXML
    private ImageView imageView;
    private Image image;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    private ConfirmationStatus status = ConfirmationStatus.REJECTED;

    public ScreenshotPreviewController(Image aImageToShow) {
        image = aImageToShow;
    }

    @FXML
    public void initialize() {
        super.initialize();
        okButton.setOnMouseClicked((event) -> {
            status = ConfirmationStatus.CONFIRMED;
            killThisWindow();
        });
        cancelButton.setOnMouseClicked((event) -> {
            status = ConfirmationStatus.REJECTED;
            killThisWindow();
        });

        imageView.setImage(image);
        imageView.setFitHeight(0);
        imageView.setFitWidth(0);
    }

    public ConfirmationStatus getStatus() {
        return status;
    }

}
