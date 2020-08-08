package pl.kurcaba.learn.helper.gui.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.controlls.CommandButton;
import pl.kurcaba.learn.helper.gui.controlls.LearnSetListView;
import pl.kurcaba.learn.helper.gui.controlls.LearnSetTable;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainWindowController {


    private GuiModelBroker guiModelBroker;

    @FXML
    private CommandButton newSet;

    @FXML
    private TextField name;

    @FXML
    private TextField definition;

    @FXML
    private Button addImage;

    @FXML
    private CommandButton addCase;

    @FXML
    private CommandButton saveSet;

    @FXML
    private LearnSetListView mainList;

    @FXML
    private LearnSetTable mainTable;

    public void initController(GuiModelBroker aGuiModelBroker) throws IOException {

        guiModelBroker = aGuiModelBroker;

        mainTable.initTable(new DeleteCaseCommand(guiModelBroker, this));
        mainList.setCommand(new LearnSetListFocusedCommand(guiModelBroker, this));
        refreshMainListData();
        initButtons();
    }


    private void initButtons() {
        newSet.setCommand(new CreateSetCommand(guiModelBroker, this));
        addCase.setCommand(new AddCaseCommand(guiModelBroker, this));
        saveSet.setCommand(new SaveSetCommand(guiModelBroker, this));

        addImage.setOnMouseClicked(event -> {
//            ScreenCapturer capturer = new ScreenCapturer();
//            Optional<WritableImage> image = capturer.openScreenshotWindow();
//            image.ifPresent(writableImage -> lastScreenshot = writableImage);
        });
    }

    void refreshTableData() {
        mainTable.getItems().clear();
        mainTable.getItems().addAll(guiModelBroker.getCaseViews().stream().collect(Collectors.toList()));
    }

    String displayInputDialog(String aDialogName, String aTitle) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(aTitle);
        dialog.setContentText(aDialogName);
        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    void refreshMainListData() throws IOException {
        List<LearnSetName> learnSetsNames = guiModelBroker.getAllSetsNames();
        mainList.setItems(FXCollections.observableArrayList(learnSetsNames));
    }

    String getNameFieldText() {
        return name.getText();
    }

    String getDefinitionFieldText() {
        return definition.getText();
    }

    void removeViewFromTable(LearnCaseView aView)
    {
        mainTable.getItems().remove(aView);
    }

    Optional<LearnCaseView> getSelectedCaseView() {
        return Optional.ofNullable(mainTable.getSelectionModel().getSelectedItem());
    }

    LearnSetName getFocusedLearnSet()
    {
        return mainList.getSelectionModel().getSelectedItem();
    }

}
