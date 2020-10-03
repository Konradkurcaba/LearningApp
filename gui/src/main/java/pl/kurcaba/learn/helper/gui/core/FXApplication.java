package pl.kurcaba.learn.helper.gui.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.controller.main.MainWindowController;
import pl.kurcaba.learn.helper.log.LoggerExceptionHandler;
import pl.kurcaba.learn.helper.persistence.LearnSetDaoIf;

import java.net.URL;

public class FXApplication extends Application
{
    @Override
    public void start(Stage aPrimaryStage) throws Exception
    {

        Thread.setDefaultUncaughtExceptionHandler(new LoggerExceptionHandler());

        URL mainFxml = FXApplication.class.getClassLoader()
                .getResource("fxml/main_panel.fxml");

        MainWindowController mainViewController = new MainWindowController();

        FXMLLoader loader = new FXMLLoader(mainFxml);
        loader.setController(mainViewController);
        Parent root = loader.load();

        GuiDataManager manager = new GuiDataManager();
        LearnSetDaoIf daoIf = manager.initializeDataManager();
        mainViewController.initController(new GuiModelBroker(daoIf), aPrimaryStage);

        Scene mainScene = new Scene(root);
        mainScene.setFill(Color.TRANSPARENT);
        mainScene.getStylesheets().add("style/style.css");


        aPrimaryStage.setScene(mainScene);
        aPrimaryStage.getIcons().add(new Image("images/icon.png"));
        aPrimaryStage.initStyle(StageStyle.TRANSPARENT);
        aPrimaryStage.setTitle("Aplikacja do nauki");
        aPrimaryStage.show();

    }
}
