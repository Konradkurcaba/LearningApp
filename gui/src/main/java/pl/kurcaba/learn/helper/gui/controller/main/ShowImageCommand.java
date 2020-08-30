package pl.kurcaba.learn.helper.gui.controller.main;

import javafx.scene.image.WritableImage;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.controller.screenshot.ConfirmImageDialog;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.util.Optional;

public class ShowImageCommand extends MainWindowCommand {
    public ShowImageCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand()
    {
        Optional<LearnCaseView> viewCase = windowController.getSelectedCaseView();
        if (viewCase.isPresent()) {
            Optional<WritableImage> aImageToShow = viewCase.get().getImage();
            ConfirmImageDialog imageDialog = new ConfirmImageDialog();
            aImageToShow.ifPresent(imageDialog::showDialog);
        }
    }

    @Override
    public boolean canBeExecuted() {
        return windowController.getSelectedCaseView().isPresent()
                && windowController.getSelectedCaseView().get().getImage().isPresent();
    }
}
