package pl.kurcaba.learn.helper.gui.addcase.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.screen.ConfirmationStatus;
import pl.kurcaba.learn.helper.gui.screen.ScreenCapturer;

import java.util.Optional;

public class AddCaseWindowController {

    @FXML
    private TextField nameTf;

    @FXML
    private TextField definitionTf;

    @FXML
    private Button makeScreenButton;
    private Optional<WritableImage> screen;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private CheckBox choseCheckBox;

    private ConfirmationStatus userAction = ConfirmationStatus.REJECTED;

    private Stage mainStage;

    @FXML
    public void initialize() {
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
        mainStage.setIconified(true);
        ScreenCapturer capturer = new ScreenCapturer();
        screen = capturer.openScreenshotWindow();
        screen.ifPresent(screen -> choseCheckBox.setSelected(true));
        mainStage.setIconified(false);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
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
        return screen;
    }

    private void killThisWindow() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

}
