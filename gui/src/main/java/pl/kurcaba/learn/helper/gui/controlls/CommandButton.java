package pl.kurcaba.learn.helper.gui.controlls;

import javafx.scene.Node;
import javafx.scene.control.Button;
import pl.kurcaba.learn.helper.gui.controller.main.CommandIf;

public class CommandButton extends Button {

    public CommandButton() {
    }

    private CommandIf command;

    public CommandButton(String s) {
        super(s);
    }

    public CommandButton(String s, Node node) {
        super(s, node);
    }

    public void setCommand(CommandIf aCommand) {
        command = aCommand;
        this.setOnMouseClicked(clickEvent -> {
            command.executeCommand();
        });
    }

    public void updateState()
    {
        disableProperty().setValue(!command.canBeExecuted());
    }
}
