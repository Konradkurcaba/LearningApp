package pl.kurcaba.learn.helper.gui.dialogs.input;

import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowDisplayer;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;

import java.nio.file.Path;
import java.util.Optional;

public class InputDialogDisplayer extends AbstractWindowDisplayer<TextInputWindowController> {

    private static final Path INPUT_DIALOG_FXML = Path.of("fxml/text_input_dialog.fxml");

    @Override
    protected TextInputWindowController createController() {
        return new TextInputWindowController();
    }

    public Optional<String> showInputDialog(String aText)
    {
        Stage mainStage = prepareStage(INPUT_DIALOG_FXML);
        getController().setDisplayedText(aText);
        mainStage.showAndWait();
        if(getController().getStatus().equals(ConfirmationStatus.CONFIRMED))
        {
            return Optional.of(getController().getText());
        }
        return Optional.empty();
    }
}
