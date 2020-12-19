package pl.kurcaba.learn.helper.gui.dialogs.addcase;

import javafx.scene.image.WritableImage;
import pl.kurcaba.learn.helper.gui.backend.AbstractCommand;
import pl.kurcaba.learn.helper.gui.controller.screenshot.ConfirmImageDialog;
import pl.kurcaba.learn.helper.gui.controller.screenshot.ScreenCaptureController;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;

import java.util.Optional;

public class AddScreenshotCmd extends AbstractCommand<AddCaseWindowController>
{
    public AddScreenshotCmd(AddCaseWindowController aAddCaseController)
    {
        super(aAddCaseController);
    }

    @Override
    public void executeCommand()
    {
        getWindowController().setIconified(true);

        ScreenCaptureController capturer = new ScreenCaptureController();
        Optional<WritableImage> newScreen = capturer.openScreenshotWindow();

        newScreen.ifPresent(screen -> {
            ConfirmImageDialog imageDialog = new ConfirmImageDialog();
            ConfirmationStatus status = imageDialog.showDialog(screen);
            if(status.equals(ConfirmationStatus.CONFIRMED))
            {
                getWindowController().addScreenshot(screen);
            }
        });

        getWindowController().setIconified(false);
    }

    @Override
    public boolean canBeExecuted()
    {
        return true;
    }
}
