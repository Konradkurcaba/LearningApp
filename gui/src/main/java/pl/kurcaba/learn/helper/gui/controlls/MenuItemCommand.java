package pl.kurcaba.learn.helper.gui.controlls;

import javafx.scene.control.MenuItem;
import pl.kurcaba.learn.helper.gui.backend.CommandIf;

public class MenuItemCommand extends MenuItem {

    public MenuItemCommand(String s) {
        super(s);
    }

    public void setCommand(CommandIf aCommand)
    {
        setOnAction( aEvent -> aCommand.executeCommand());
    }

}
