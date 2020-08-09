package pl.kurcaba.learn.helper.gui.controlls;

import javafx.scene.control.ListView;
import pl.kurcaba.learn.helper.gui.main.controller.AbstractCommand;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

public class LearnSetListView extends ListView<LearnSetName> {

    public void setCommand(AbstractCommand aCommand)
    {
        getSelectionModel().selectedItemProperty().addListener(observable -> {
            aCommand.executeCommand();
        });
    }

}
