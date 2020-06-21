package pl.kurcaba.learn.helper.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.core.GuiDataManager;
import pl.kurcaba.learn.helper.learnset.model.LearnDataManager;

import java.net.URL;

public class FXApplication extends Application
{
    @Override
    public void start(Stage aPrimaryStage) throws Exception
    {

        URL mainFxml = FXApplication.class.getClassLoader()
                .getResource("main_panel.fxml");

        GuiDataManager guiDataManager = new GuiDataManager();
        LearnDataManager dataManager = guiDataManager.initializeDataManager();

        MainWindowController mainViewController = new MainWindowController();
        mainViewController.setDataManager(dataManager);

        FXMLLoader loader = new FXMLLoader(mainFxml);
        loader.setController(mainViewController);
        Parent root = loader.load();

        aPrimaryStage.setScene(new Scene(root));
        aPrimaryStage.setTitle("Aplikacja do nauki");
        aPrimaryStage.show();

    }
}
