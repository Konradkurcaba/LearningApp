package pl.kurcaba.learn.helper.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class FXApplication extends Application
{
    @Override
    public void start(Stage aPrimaryStage) throws Exception
    {

        URL mainFxml = FXApplication.class.getClassLoader()
                .getResource("main.fxml");

        FXMLLoader loader = new FXMLLoader(mainFxml);
        Parent root = loader.load();
        aPrimaryStage.setScene(new Scene(root));
        aPrimaryStage.show();

    }
}
