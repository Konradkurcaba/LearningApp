package pl.kurcaba.learn.helper.gui.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowController;

public class ConfirmDialogController extends AbstractWindowController
{

    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label textLabel;
    @FXML
    private TextField textField;

    private ConfirmationStatus status = ConfirmationStatus.REJECTED;

    @FXML
    public void initialize() {
        super.initialize();
        okButton.setOnMouseClicked(mouseEvent -> {
            status = ConfirmationStatus.CONFIRMED;
            killThisWindow();
        });
        cancelButton.setOnMouseClicked(mouseEvent -> {
            status = ConfirmationStatus.REJECTED;
            killThisWindow();
        });
    }

    public void setLabelText(String aText)
    {
        textLabel.setText(aText);
    }

    public ConfirmationStatus getStatus() {
        return status;
    }

    private void killThisWindow() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }


}
