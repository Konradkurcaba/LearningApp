package pl.kurcaba.learn.helper.gui.dialogs.addcase;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowController;
import pl.kurcaba.learn.helper.gui.controller.screenshot.ConfirmImageDialog;
import pl.kurcaba.learn.helper.gui.controller.screenshot.ScreenCaptureController;
import pl.kurcaba.learn.helper.gui.controlls.CommandButton;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;

import java.util.Optional;

public class AddCaseWindowController extends AbstractWindowController
{

    @FXML
    protected TextField nameTf;

    @FXML
    protected TextField definitionTf;

    @FXML
    private CommandButton makeScreenButton;

    @FXML
    private CommandButton removeScreenButton;

    protected WritableImage screen;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ListView imageListView;


    private ConfirmationStatus userAction = ConfirmationStatus.REJECTED;

    @FXML
    public void initialize()
    {
        super.initialize();
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


    private void makeScreen() {
        getStage().setIconified(true);

        ScreenCaptureController capturer = new ScreenCaptureController();
        Optional<WritableImage> newScreen = capturer.openScreenshotWindow();

        newScreen.ifPresent(screen -> {
            ConfirmImageDialog imageDialog = new ConfirmImageDialog();
            ConfirmationStatus status = imageDialog.showDialog(screen);
            if(status.equals(ConfirmationStatus.CONFIRMED))
            {
                this.screen = screen;
            }
        });

        getStage().setIconified(false);
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

}
