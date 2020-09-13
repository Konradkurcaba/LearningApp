package pl.kurcaba.learn.helper.gui.controlls;

import javafx.scene.control.MenuItem;
import pl.kurcaba.learn.helper.gui.controller.main.MainWindowCommand;

public class MenuItemCommand extends MenuItem {

    public MenuItemCommand(String s) {
        super(s);
    }

    public void setCommand(MainWindowCommand aCommand)
    {
        setOnAction( aEvent -> aCommand.executeCommand());
    }

}
