package pl.kurcaba.learn.helper.gui.dialogs.confirm;

import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowDisplayer;

import java.nio.file.Path;

public class ConfirmDialogDisplayer extends AbstractWindowDisplayer<ConfirmDialogController> {

    public static final Path CONFIRM_DIALOG_FXML = Path.of("fxml/confirm_dialog.fxml");
    public static final String EMPTY_TITLE = "";

    @Override
    protected ConfirmDialogController createController() {
        return new ConfirmDialogController();
    }

    public ConfirmationStatus showConfirmWindow(String aText)
    {
        Stage stage = prepareStage(CONFIRM_DIALOG_FXML);
        getController().setDisplayedText(aText);
        stage.showAndWait();
        return getController().getStatus();
    }
}
