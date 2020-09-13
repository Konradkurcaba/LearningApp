package pl.kurcaba.learn.helper.gui.controlls;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import pl.kurcaba.learn.helper.gui.controller.main.MainWindowCommand;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

public class LearnSetListView extends ListView<LearnSetName> {


    private void initContextMenu(MainWindowCommand aDeleteCmd)
    {
        ContextMenu menu = new ContextMenu();
        MenuItemCommand deleteItem = new MenuItemCommand("Usuń");
        deleteItem.setCommand(aDeleteCmd);
        menu.getItems().add(deleteItem);
        contextMenuProperty().setValue(menu);
    }

    public void setFocusCommand(MainWindowCommand focusCommand)
    {
        getSelectionModel().selectedItemProperty()
                .addListener(observable -> focusCommand.executeCommand());
    }

    public void setDeleteSetCommand(MainWindowCommand deleteSetCommand)
    {
        initContextMenu(deleteSetCommand);
    }


}
