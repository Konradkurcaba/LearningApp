package pl.kurcaba.learn.helper.gui.controller.addcase;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowController;
import pl.kurcaba.learn.helper.gui.controller.screenshot.ScreenCaptureController;
import pl.kurcaba.learn.helper.gui.controller.screenshot.ConfirmImageDialog;
import pl.kurcaba.learn.helper.gui.dialogs.ConfirmationStatus;

import java.util.Optional;

public class AddCaseWindowController extends AbstractWindowController
{

    @FXML
    private TextField nameTf;

    @FXML
    private TextField definitionTf;

    @FXML
    private Button makeScreenButton;
    private WritableImage screen;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private CheckBox choseCheckBox;

    private ConfirmationStatus userAction = ConfirmationStatus.REJECTED;

    @FXML
    public void initialize() {
        super.initialize();
        initCheckBox();
        initButtons();
    }

    private void initButtons() {
        okButton.setOnMouseClicked(event -> {
            userAction = ConfirmationStatus.CONFIRMED;
            killThisWindow();
        });
        cancelButton.setOnMouseClicked(event -> killThisWindow());
        makeScreenButton.setOnMouseClicked(event -> makeScreen());
    }

    private void initCheckBox() {
        choseCheckBox.setAllowIndeterminate(false);
        choseCheckBox.setDisable(true);
        choseCheckBox.setStyle("-fx-opacity: 1");
    }

    private void makeScreen() {
        getStage().setIconified(true);

        ScreenCaptureController capturer = new ScreenCaptureController();
        Optional<WritableImage> newScreen = capturer.openScreenshotWindow();

        newScreen.ifPresent(screen -> {
            ConfirmationStatus status = ConfirmImageDialog.showDialog(screen);
            if(status.equals(ConfirmationStatus.CONFIRMED))
            {
                this.screen = screen;
                choseCheckBox.setSelected(true);
            }
        });

        getStage().setIconified(false);
    }

    public void setMainStage(Stage aStage) {
        setStage(aStage);
    }

    public ConfirmationStatus getUserAction() {
        return userAction;
    }

    public String getNewCaseName() {
        return nameTf.getText();
    }

    public String getNewCaseDefinition() {
        return definitionTf.getText();
    }

    public Optional<WritableImage> getNewCasePicture() {
        return Optional.ofNullable(screen);
    }

    private void killThisWindow() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

}
