package pl.kurcaba.learn.helper.gui.dialogs.addcase;

import javafx.scene.image.WritableImage;
import pl.kurcaba.learn.helper.gui.backend.AbstractCommand;
import pl.kurcaba.learn.helper.gui.controller.CloseWindowCommand;

import java.util.List;

public class ConfirmAddCaseWindowCmd extends AbstractCommand<AddCaseWindowController>
{

    public ConfirmAddCaseWindowCmd(AddCaseWindowController windowController)
    {
        super(windowController);
    }

    @Override
    public void executeCommand()
    {
        String name = getWindowController().getNewCaseName();
        String definition = getWindowController().getNewCaseDefinition();
        List<WritableImage> images = getWindowController().getScreenshots();
        getWindowController().setCreatedDto(new NewCaseDto(name, definition, images));

        new CloseWindowCommand(getWindowController()).executeCommand();
    }

    @Override
    public boolean canBeExecuted()
    {
        return false;
    }
}
