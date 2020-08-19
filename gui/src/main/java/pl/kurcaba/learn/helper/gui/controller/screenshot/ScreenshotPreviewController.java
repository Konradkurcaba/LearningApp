package pl.kurcaba.learn.helper.gui.controller.screenshot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.screen.ConfirmationStatus;

public class ScreenshotPreviewController {

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
        okButton.setOnMouseClicked(mouseEvent -> {
            status = ConfirmationStatus.CONFIRMED;
            killThisWindow();
        });
        cancelButton.setOnMouseClicked(mouseEvent -> {
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
