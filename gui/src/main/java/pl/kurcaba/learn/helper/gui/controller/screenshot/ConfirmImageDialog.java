package pl.kurcaba.learn.helper.gui.controller.screenshot;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.core.FXApplication;
import pl.kurcaba.learn.helper.gui.dialogs.ConfirmationStatus;

import java.io.IOException;
import java.net.URL;

public class ConfirmImageDialog {

    private static final Logger logger = LogManager.getLogger(ConfirmationStatus.class);
    public static final String CONFIRM_IMAGE_FXML = "fxml/screen_preview_panel.fxml";

    public static ConfirmationStatus showDialog(Image aImageToShow) {
        URL screenPreviewPanel = FXApplication.class.getClassLoader()
                .getResource(CONFIRM_IMAGE_FXML);

        ScreenshotPreviewController screenshotController = new ScreenshotPreviewController(aImageToShow);

        FXMLLoader loader = new FXMLLoader(screenPreviewPanel);
        loader.setController(screenshotController);

        try {
            Parent root = loader.load();
            Stage newWindow = new Stage();
            newWindow.setTitle("Podgląd");
            newWindow.initModality(Modality.APPLICATION_MODAL);
            Scene newScene = new Scene(root);
            newWindow.setScene(newScene);
            newWindow.showAndWait();
            return screenshotController.getStatus();

        } catch (IOException aEx) {
            logger.error(aEx);
            return ConfirmationStatus.REJECTED;
        }
    }


}
