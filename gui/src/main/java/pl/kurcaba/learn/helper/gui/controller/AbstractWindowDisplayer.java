package pl.kurcaba.learn.helper.gui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public abstract class AbstractWindowDisplayer<T extends AbstractWindowController>
{
    private static final Logger logger = LogManager.getLogger(AbstractWindowDisplayer.class);

    private T controller;


    protected Stage prepareStage(String aFxmlFileLocation)
    {

        URL fxmlPath = AbstractWindowDisplayer.class.getClassLoader()
                .getResource(aFxmlFileLocation);
        FXMLLoader loader = new FXMLLoader(fxmlPath);

        T controller = createController();
        this.controller = controller;
        loader.setController(controller);

        Parent root = null;
        try
        {
            root = loader.load();
        } catch (IOException e) {
            //this really shouldn't happen
            logger.error("A problem has occurred:", e);
        }

        Stage stage = createStage();
        controller.setStage(stage);
        Scene newScene = createScene(root);
        stage.setScene(newScene);
        stage.getIcons().add(new Image("images/icon.png"));

        controller.invokeAfterCreateGui();

        return stage;
    }

    protected abstract T createController();

    protected Scene createScene(Parent root) {
        Scene newScene = new Scene(root);
        newScene.setFill(Color.TRANSPARENT);
        newScene.getStylesheets().add("style/style.css");
        return newScene;
    }

    protected Stage createStage()
    {
        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initStyle(StageStyle.TRANSPARENT);
        return newStage;
    }

     protected T getController() {
        return controller;
    }
}
