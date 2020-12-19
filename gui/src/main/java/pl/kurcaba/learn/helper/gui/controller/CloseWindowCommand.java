package pl.kurcaba.learn.helper.gui.controller;

import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.backend.CommandIf;

public class CloseWindowCommand implements CommandIf
{
    private final Stage stage;

    public CloseWindowCommand(Stage aStage)
    {
        stage = aStage;
    }

    @Override
    public void executeCommand()
    {
        stage.close();
    }

    @Override
    public boolean canBeExecuted() {
        return true;
    }
}
