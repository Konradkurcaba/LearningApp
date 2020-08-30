package pl.kurcaba.learn.helper.gui.controller.screenshot;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowController;
import pl.kurcaba.learn.helper.gui.controlls.CommandButton;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;

public class ScreenshotPreviewController extends AbstractWindowController {

    @FXML
    private ImageView imageView;
    private Image image;
    @FXML
    private CommandButton okButton;
    @FXML
    private CommandButton cancelButton;

    private ConfirmationStatus status = ConfirmationStatus.REJECTED;

    public ScreenshotPreviewController(Image aImageToShow) {
        image = aImageToShow;
    }

    @FXML
    public void initialize() {
        super.initialize();
        okButton.setCommand(() -> {
            status = ConfirmationStatus.CONFIRMED;
            killThisWindow();
        });
        cancelButton.setCommand(() -> {
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

    private void killThisWindow() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

}
