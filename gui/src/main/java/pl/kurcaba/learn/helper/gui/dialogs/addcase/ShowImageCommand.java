package pl.kurcaba.learn.helper.gui.dialogs.addcase;

import javafx.scene.image.WritableImage;
import pl.kurcaba.learn.helper.gui.backend.AbstractCommand;
import pl.kurcaba.learn.helper.gui.controller.screenshot.ConfirmImageDialog;

public class ShowImageCommand extends AbstractCommand<AddCaseWindowController>
{

    public ShowImageCommand(AddCaseWindowController windowController)
    {
        super(windowController);
    }

    @Override
    public void executeCommand()
    {
        WritableImage aImageToShow = getWindowController().getSelectedImage();
        ConfirmImageDialog imageDialog = new ConfirmImageDialog();
        imageDialog.showDialog(aImageToShow);
    }

    @Override
    public boolean canBeExecuted()
    {
        return getWindowController().isAnyScreenshotSelected();
    }
}
