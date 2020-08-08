package pl.kurcaba.learn.helper.gui.controlls;

import javafx.scene.control.MenuItem;
import pl.kurcaba.learn.helper.gui.controller.AbstractCommand;

public class MenuItemCommand extends MenuItem {

    public MenuItemCommand(String s) {
        super(s);
    }

    public void setCommand(AbstractCommand aCommand)
    {
        setOnAction( aEvent -> {
            aCommand.executeCommand();
        });
    }

}
