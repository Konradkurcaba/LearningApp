package pl.kurcaba.learn.helper.gui.dialogs.input;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmDialogController;

public class TextInputWindowController extends ConfirmDialogController {

    @FXML
    private TextField textField;

    public String getText() {
        return textField.getText();
    }

}