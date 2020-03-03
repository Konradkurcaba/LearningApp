package pl.kurcaba.learn.helper.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.learnset.controller.LearnViewController;
import pl.kurcaba.learn.helper.learnset.controller.MainWindowController;
import pl.kurcaba.learn.helper.learnset.model.LearnCase;
import pl.kurcaba.learn.helper.learnset.model.LearnSetLogic;
import pl.kurcaba.learn.helper.learnset.model.LearnSetModel;

import java.net.URL;

public class FXApplication extends Application
{
    @Override
    public void start(Stage aPrimaryStage) throws Exception
    {

        URL mainFxml = FXApplication.class.getClassLoader()
                .getResource("main_panel.fxml");


        LearnCase learnCase = new LearnCase("desk","biurko",null);
        LearnCase learnCase1 = new LearnCase("god","bóg",null);
        LearnCase learnCase2 = new LearnCase("weed","trawa",null);
        LearnSetModel learnSetModel = new LearnSetModel();
        learnSetModel.getLearnSetCases().add(learnCase);
        learnSetModel.getLearnSetCases().add(learnCase1);
        learnSetModel.getLearnSetCases().add(learnCase2);
        LearnSetLogic learnSetLogic = new LearnSetLogic(learnSetModel);
        LearnViewController controller = new LearnViewController(learnSetLogic);
        MainWindowController controller1 = new MainWindowController();

        FXMLLoader loader = new FXMLLoader(mainFxml);
        loader.setController(controller1);
        Parent root = loader.load();
        controller.reloadView();
        controller.configureEvents();
        aPrimaryStage.setScene(new Scene(root));
        aPrimaryStage.show();

    }
}
