package pl.kurcaba.learn.helper.gui.controller.main;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowController;
import pl.kurcaba.learn.helper.gui.controller.addcase.NewCaseDto;
import pl.kurcaba.learn.helper.gui.controller.addcase.NewCaseWindow;
import pl.kurcaba.learn.helper.gui.controlls.CommandButton;
import pl.kurcaba.learn.helper.gui.controlls.LearnSetListView;
import pl.kurcaba.learn.helper.gui.controlls.LearnSetTable;
import pl.kurcaba.learn.helper.gui.dialogs.ConfirmDialogUtil;
import pl.kurcaba.learn.helper.gui.dialogs.ConfirmationStatus;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainWindowController extends AbstractWindowController
{

    private GuiModelBroker guiModelBroker;

    @FXML
    private CommandButton newSet;

    @FXML
    private CommandButton addNewCase;

    @FXML
    private CommandButton saveSet;

    @FXML
    private LearnSetListView learnSetListView;

    @FXML
    private LearnSetTable learnSetTable;

    public void initController(GuiModelBroker aGuiModelBroker, Stage aMainStage) throws IOException {

        guiModelBroker = aGuiModelBroker;
        setStage(aMainStage);

        initButtons();
        learnSetTable.initTable(new DeleteCaseCommand(guiModelBroker, this)
                , new ShowImageCommand(guiModelBroker, this));
        learnSetListView.setCommand(new LearnSetFocusedCmd(guiModelBroker, this));
        refreshMainListData();
    }

    private void initButtons() {
        newSet.setCommand(new CreateSetCommand(guiModelBroker, this));
        addNewCase.setCommand(new AddCaseCommand(guiModelBroker, this));
        addNewCase.setDisable(true);
        saveSet.setCommand(new SaveSetCommand(guiModelBroker, this));
        saveSet.disableProperty().bind(guiModelBroker.getUnsavedChangesProperty().not());
        addNewCase.disableProperty().bind(guiModelBroker.getIsLearnSetChosenProperty().not());
    }

    void refreshSetData() {
        learnSetTable.getItems().clear();
        learnSetTable.getItems().addAll(new ArrayList<>(guiModelBroker.getCaseViews()));
    }

    Optional<String> displayTextInputDialog(String aDialogName, String aTitle) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(aTitle);
        dialog.setContentText(aDialogName);
        return dialog.showAndWait();
    }

    ConfirmationStatus displayConfirmDialog(String aTextToDisplay)
    {
        return ConfirmDialogUtil.showConfirmDialog(aTextToDisplay);
    }

    void refreshMainListData() throws IOException {
        List<LearnSetName> learnSetsNames = guiModelBroker.getAllSetsNames();
        boolean isListEmpty = learnSetListView.getItems().isEmpty();
        learnSetListView.setItems(FXCollections.observableArrayList(learnSetsNames));
        if(isListEmpty)
        {
            learnSetListView.getSelectionModel().selectFirst();
        }
    }

    void removeViewFromTable(LearnCaseView aView) {
        learnSetTable.getItems().remove(aView);
    }

    Optional<LearnCaseView> getSelectedCaseView() {
        return Optional.ofNullable(learnSetTable.getSelectionModel().getSelectedItem());
    }

    Optional<LearnSetName> getFocusedLearnSet() {
        return Optional.ofNullable(learnSetListView.getSelectionModel().getSelectedItem());
    }

    void setMainWindowIconified(Boolean aValue) {
        getStage().setIconified(aValue);
    }

    NewCaseDto showNewCaseWindow() throws IOException {
        return NewCaseWindow.showNewCaseWindow();
    }

}
