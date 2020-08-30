package pl.kurcaba.learn.helper.gui.controller.main;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowController;
import pl.kurcaba.learn.helper.gui.controlls.CommandButton;
import pl.kurcaba.learn.helper.gui.controlls.LearnSetListView;
import pl.kurcaba.learn.helper.gui.controlls.LearnSetTable;
import pl.kurcaba.learn.helper.gui.dialogs.addcase.NewCaseDto;
import pl.kurcaba.learn.helper.gui.dialogs.addcase.NewCaseWindowDisplayer;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmDialogDisplayer;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;
import pl.kurcaba.learn.helper.gui.dialogs.input.InputDialogDisplayer;
import pl.kurcaba.learn.helper.gui.dialogs.learn.LearnPanelDisplayer;
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
    private CommandButton startButton;

    @FXML
    private LearnSetListView learnSetListView;
    private LearnSetName currentLearnSetName;

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
        addNewCase.updateState();
        saveSet.setCommand(new SaveSetCommand(guiModelBroker, this));
        saveSet.disableProperty().bind(guiModelBroker.getUnsavedChangesProperty().not());
        addNewCase.disableProperty().bind(guiModelBroker.isLearnSetChosenProperty().not());
        startButton.setCommand(new StartLearnCommand(guiModelBroker, this));
        startButton.updateState();
    }

    void refreshSetData()
    {
        learnSetTable.getItems().clear();
        learnSetTable.getItems().addAll(new ArrayList<>(guiModelBroker.getCaseViews()));
        startButton.updateState();
    }

    Optional<String> displayTextInputDialog(String aTextToDisplay) {
        InputDialogDisplayer inputDialogDisplayer = new InputDialogDisplayer();
        return inputDialogDisplayer.showInputDialog(aTextToDisplay);
    }

    ConfirmationStatus displayConfirmDialog(String aTextToDisplay)
    {
        ConfirmDialogDisplayer confirmDialogUtil = new ConfirmDialogDisplayer();
        return confirmDialogUtil.showConfirmWindow(aTextToDisplay);
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

    Optional<LearnSetName> getDisplayedLearnSet()
    {
        return Optional.ofNullable(currentLearnSetName);
    }

    public void setDisplayedLearnSet(LearnSetName aLearnSetName) {
        this.currentLearnSetName = aLearnSetName;
    }

    void changeFocus(LearnSetName aLearnSetName)
    {
        learnSetListView.getSelectionModel().select(aLearnSetName);
    }

    void setMainWindowIconified(Boolean aValue) {
        getStage().setIconified(aValue);
    }

    NewCaseDto showNewCaseWindow() {
        NewCaseWindowDisplayer windowDisplayer = new NewCaseWindowDisplayer();
        return windowDisplayer.showNewCaseWindow();
    }

    void showLearnPanel(boolean aDisplayName,boolean aDisplayDefinition
            , boolean aDisplayImage, List<LearnCaseView> aCases)
    {
        LearnPanelDisplayer panelDisplayer = new LearnPanelDisplayer(aDisplayName, aDisplayDefinition
                , aDisplayImage, aCases);
        panelDisplayer.displayWindow();
    }

}
