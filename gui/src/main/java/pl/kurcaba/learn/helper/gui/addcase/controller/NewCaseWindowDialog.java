package pl.kurcaba.learn.helper.gui.addcase.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.core.FXApplication;
import pl.kurcaba.learn.helper.gui.screen.ConfirmationStatus;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class NewCaseWindowDialog {

    private static final Logger logger = LogManager.getLogger(NewCaseWindowDialog.class);

    public static NewCaseDto showNewCaseWindow() throws IOException {

        URL screenPreviewPanel = FXApplication.class.getClassLoader()
                .getResource("add_new_case_panel.fxml");

        AddCaseWindowController windowController = new AddCaseWindowController();

        FXMLLoader loader = new FXMLLoader(screenPreviewPanel);
        loader.setController(windowController);

        Parent root = loader.load();
        Stage newStage = new Stage();
        windowController.setMainStage(newStage);
        newStage.setTitle("Dodawanie nowego pojęcia");
        newStage.initModality(Modality.APPLICATION_MODAL);
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.showAndWait();

        String name = windowController.getNewCaseName();
        String definition = windowController.getNewCaseDefinition();
        Optional<WritableImage> image = windowController.getNewCasePicture();
        ConfirmationStatus status = windowController.getUserAction();

        return new NewCaseDto(name, definition, image, status);
    }


}
