package pl.kurcaba.learn.helper.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.main.controller.MainWindowController;
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

        MainWindowController mainViewController = new MainWindowController();

        FXMLLoader loader = new FXMLLoader(mainFxml);
        loader.setController(mainViewController);
        Parent root = loader.load();

        GuiDataManager manager = new GuiDataManager();
        LearnDataManager dataManager = manager.initializeDataManager();
        mainViewController.initController(new GuiModelBroker(dataManager), aPrimaryStage);

        aPrimaryStage.setScene(new Scene(root));
        aPrimaryStage.setTitle("Aplikacja do nauki");
        aPrimaryStage.show();

    }
}
