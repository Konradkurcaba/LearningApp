package pl.kurcaba.learn.helper.gui;

import javafx.application.Application;
import javafx.stage.Stage;

import java.net.URL;

public class FXApplication extends Application
{
    @Override
    public void start(Stage aPrimaryStage) throws Exception
    {

        URL mainFxml = FXApplication.class.getClassLoader()
                .getResource("main_panel.fxml");


//
//        LearnSetManager learnSetManager = new LearnSetManager(learnSetDto);
//        LearnViewController controller = new LearnViewController(learnSetManager);
//        MainWindowController controller1 = new MainWindowController();
//
//        FXMLLoader loader = new FXMLLoader(mainFxml);
//        loader.setController(controller1);
//        Parent root = loader.load();
//        controller.reloadView();
//        controller.configureEvents();
//        aPrimaryStage.setScene(new Scene(root));
//        aPrimaryStage.show();

    }
}
