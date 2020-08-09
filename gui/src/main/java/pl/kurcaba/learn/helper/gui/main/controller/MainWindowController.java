package pl.kurcaba.learn.helper.gui.main.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.controlls.CommandButton;
import pl.kurcaba.learn.helper.gui.controlls.LearnSetListView;
import pl.kurcaba.learn.helper.gui.controlls.LearnSetTable;
import pl.kurcaba.learn.helper.gui.screen.ScreenCapturer;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainWindowController {


    private GuiModelBroker guiModelBroker;
    private Stage mainStage;

    @FXML
    private CommandButton newSet;

    @FXML
    private TextField name;

    @FXML
    private TextField definition;

    @FXML
    private CommandButton addImage;
    private WritableImage lastScreenShot;

    @FXML
    private CommandButton addCase;

    @FXML
    private CommandButton saveSet;

    @FXML
    private LearnSetListView learnSetListView;

    @FXML
    private LearnSetTable learnSetTable;

    public void initController(GuiModelBroker aGuiModelBroker, Stage aMainStage) throws IOException {

        guiModelBroker = aGuiModelBroker;
        mainStage = aMainStage;

        learnSetTable.initTable(new DeleteCaseCommand(guiModelBroker, this));
        learnSetListView.setCommand(new LearnSetListFocusedCommand(guiModelBroker, this));
        refreshMainListData();
        initButtons();
    }


    private void initButtons() {
        newSet.setCommand(new CreateSetCommand(guiModelBroker, this));
        addCase.setCommand(new AddCaseCommand(guiModelBroker, this));
        saveSet.setCommand(new SaveSetCommand(guiModelBroker, this));
        addImage.setCommand(new MakeScreenshotCommand(guiModelBroker, this));
    }

    void refreshSetData() {
        learnSetTable.getItems().clear();
        learnSetTable.getItems().addAll(guiModelBroker.getCaseViews().stream().collect(Collectors.toList()));
        lastScreenShot = null;
    }

    String displayInputDialog(String aDialogName, String aTitle) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(aTitle);
        dialog.setContentText(aDialogName);
        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    Optional<WritableImage> displayCutDialog()
    {
        ScreenCapturer capturer = new ScreenCapturer();
        return capturer.openScreenshotWindow();
    }

    void refreshMainListData() throws IOException {
        List<LearnSetName> learnSetsNames = guiModelBroker.getAllSetsNames();
        learnSetListView.setItems(FXCollections.observableArrayList(learnSetsNames));
    }

    String getNameFieldText() {
        return name.getText();
    }

    String getDefinitionFieldText() {
        return definition.getText();
    }

    void removeViewFromTable(LearnCaseView aView)
    {
        learnSetTable.getItems().remove(aView);
    }

    Optional<LearnCaseView> getSelectedCaseView() {
        return Optional.ofNullable(learnSetTable.getSelectionModel().getSelectedItem());
    }

    Optional<LearnSetName> getFocusedLearnSet()
    {
        return Optional.ofNullable(learnSetListView.getSelectionModel().getSelectedItem());
    }

    void setLastScreenShot(WritableImage newScreenShot) {
        lastScreenShot = newScreenShot;
    }

    Optional<WritableImage> getLastScreenShot() {
        return Optional.ofNullable(lastScreenShot);
    }

    void setMainWindowIconified(Boolean aValue)
    {
        mainStage.setIconified(aValue);
    }
}
