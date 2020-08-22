package pl.kurcaba.learn.helper.gui.dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.core.FXApplication;

import java.io.IOException;
import java.net.URL;

public class ConfirmDialogUtil {

    private final static Logger logger = LogManager.getLogger(ConfirmDialogUtil.class);

    public static final String CONFIRM_DIALOG_FXML = "fxml/confirm_dialog.fxml";

    public static ConfirmationStatus showConfirmDialog(String aText) {
        URL screenPreviewPanel = FXApplication.class.getClassLoader()
                .getResource(CONFIRM_DIALOG_FXML);

        ConfirmDialogController windowController = new ConfirmDialogController();

        FXMLLoader loader = new FXMLLoader(screenPreviewPanel);
        loader.setController(windowController);
        Parent root = null;
        try
        {
            root = loader.load();
        } catch (IOException e) {
            //this really shouldn't happen
            logger.error(e);
            return ConfirmationStatus.REJECTED;
        }

        windowController.setLabelText(aText);

        Stage newStage = new Stage();
        windowController.setStage(newStage);

        Scene newScene = new Scene(root);
        newScene.setFill(Color.TRANSPARENT);
        newScene.getStylesheets().add("style/style.css");
        newStage.setScene(newScene);
        newStage.showAndWait();

        return windowController.getStatus();
    }



}
