package pl.kurcaba.learn.helper.gui.controller.addcase;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.core.FXApplication;
import pl.kurcaba.learn.helper.gui.dialogs.ConfirmationStatus;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class NewCaseWindow {

    private static final Logger logger = LogManager.getLogger(NewCaseWindow.class);

    public static NewCaseDto showNewCaseWindow() throws IOException {

        URL screenPreviewPanel = FXApplication.class.getClassLoader()
                .getResource("fxml/add_new_case_panel.fxml");

        AddCaseWindowController windowController = new AddCaseWindowController();

        FXMLLoader loader = new FXMLLoader(screenPreviewPanel);
        loader.setController(windowController);

        Parent root = loader.load();
        Stage newStage = prepareStage();
        windowController.setMainStage(newStage);

        Scene newScene = new Scene(root);
        newScene.setFill(Color.TRANSPARENT);
        newScene.getStylesheets().add("style/style.css");
        newStage.setScene(newScene);
        newStage.showAndWait();

        String name = windowController.getNewCaseName();
        String definition = windowController.getNewCaseDefinition();
        Optional<WritableImage> image = windowController.getNewCasePicture();
        ConfirmationStatus status = windowController.getUserAction();

        return new NewCaseDto(name, definition, image, status);
    }

    private static Stage prepareStage()
    {
        Stage newStage = new Stage();
        newStage.initStyle(StageStyle.TRANSPARENT);

        newStage.setTitle("Dodawanie nowego pojęcia");
        newStage.initModality(Modality.APPLICATION_MODAL);
        return newStage;
    }


}