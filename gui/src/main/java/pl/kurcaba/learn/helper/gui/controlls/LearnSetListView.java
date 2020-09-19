package pl.kurcaba.learn.helper.gui.controlls;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import pl.kurcaba.learn.helper.gui.controller.main.MainWindowCommand;
import pl.kurcaba.learn.helper.gui.core.ApplicationConstants;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

import java.util.ResourceBundle;

public class LearnSetListView extends ListView<LearnSetName> {


    private void initContextMenu(MainWindowCommand aDeleteCmd)
    {
        ContextMenu menu = new ContextMenu();
        ResourceBundle textBundle = ResourceBundle.getBundle(ApplicationConstants.DEFAULT_BUNDLE_NAME);
        MenuItemCommand deleteItem = new MenuItemCommand(textBundle.getString("deleteAction"));
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
