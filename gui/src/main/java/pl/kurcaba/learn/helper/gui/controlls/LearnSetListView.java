package pl.kurcaba.learn.helper.gui.controlls;

import javafx.scene.control.ListView;
import pl.kurcaba.learn.helper.gui.controller.main.MainWindowCommand;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

public class LearnSetListView extends ListView<LearnSetName> {

    public LearnSetListView()
    { }

    public void setCommand(MainWindowCommand aCommand)
    {
        getSelectionModel().selectedItemProperty().addListener(observable -> {
            aCommand.executeCommand();
        });
    }

}
