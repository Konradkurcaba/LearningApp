package pl.kurcaba.learn.helper.gui.controlls;

import javafx.scene.Node;
import javafx.scene.control.Button;
import pl.kurcaba.learn.helper.gui.controller.AbstractCommand;

public class CommandButton extends Button {

    public CommandButton() {
    }

    public CommandButton(String s) {
        super(s);
    }

    public CommandButton(String s, Node node) {
        super(s, node);
    }

    public void setCommand(AbstractCommand aCommand) {
        this.setOnMouseClicked(clickEvent -> {
            aCommand.executeCommand();
        });
    }
}
