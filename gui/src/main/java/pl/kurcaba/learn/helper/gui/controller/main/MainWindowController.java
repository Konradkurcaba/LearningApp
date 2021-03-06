package pl.kurcaba.learn.helper.gui.controller.main;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
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
import pl.kurcaba.learn.helper.gui.dialogs.options.LearnOptions;
import pl.kurcaba.learn.helper.gui.dialogs.options.LearnOptionsDisplayer;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainWindowController extends AbstractWindowController
{

    public static final String GRAY_LINE_PNG = "images/gray-line.png";
    private GuiModelBroker guiModelBroker;

    @FXML
    private CommandButton minimizeButton;

    @FXML
    private ImageView minimizeImageView;

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

    public void initController(GuiModelBroker aGuiModelBroker, Stage aMainStage) throws IOException
    {

        guiModelBroker = aGuiModelBroker;
        setStage(aMainStage);

        initButtons();
        learnSetTable.initTable(new DeleteCaseCommand(guiModelBroker, this)
                , new ShowImageCommand(guiModelBroker, this), new EditCaseCommand(aGuiModelBroker, this));
        learnSetTable.setEditable(true);
        learnSetListView.setFocusCommand(new LearnSetFocusedCmd(guiModelBroker, this));
        learnSetListView.setDeleteSetCommand(new RemoveSetCommand(guiModelBroker, this));
        refreshMainListData();
    }

    private void initButtons()
    {
        newSet.setCommand(new CreateSetCommand(guiModelBroker, this));
        addNewCase.setCommand(new AddCaseCommand(guiModelBroker, this));
        addNewCase.updateState();
        saveSet.setCommand(new SaveSetCommand(guiModelBroker, this));
        saveSet.disableProperty().bind(guiModelBroker.getUnsavedChangesProperty().not());
        addNewCase.disableProperty().bind(guiModelBroker.isLearnSetChosenProperty().not());
        startButton.setCommand(new StartLearnCommand(guiModelBroker, this));
        startButton.updateState();
        minimizeButton.setCommand(new MinimizeWindowCommand(guiModelBroker, this));
        minimizeImageView.setImage(getImage(GRAY_LINE_PNG));
    }

    void refreshSetData()
    {
        LearnCaseView currentCase = learnSetTable.getSelectionModel().getSelectedItem();

        learnSetTable.getItems().clear();
        learnSetTable.getItems().addAll(new ArrayList<>(guiModelBroker.getCaseViews()));

        learnSetTable.getSelectionModel().select(currentCase);
        startButton.updateState();
    }

    @Override
    protected CommandIf createExitCommand(Stage aStage)
    {
        return new CloseMainWindowCommand(guiModelBroker, this);
    }

    Optional<String> displayTextInputDialog(String aTextToDisplay)
    {
        InputDialogDisplayer inputDialogDisplayer = new InputDialogDisplayer();
        return inputDialogDisplayer.showInputDialog(aTextToDisplay);
    }

    ConfirmationStatus displayConfirmDialog(String aTextToDisplay)
    {
        ConfirmDialogDisplayer confirmDialogUtil = new ConfirmDialogDisplayer();
        return confirmDialogUtil.showConfirmWindow(aTextToDisplay);
    }

    void refreshMainListData()
    {
        GetAllNamesAsyncCommand getAllNames = new GetAllNamesAsyncCommand(guiModelBroker, this);
        getAllNames.start();
    }

    void changeLearnSetNames(List<LearnSetName> names)
    {
        learnSetListView.setItems(FXCollections.observableArrayList(names));
        learnSetListView.getSelectionModel().clearSelection();
        refreshSetData();
    }

    void removeViewFromTable(LearnCaseView aView)
    {
        learnSetTable.getItems().remove(aView);
    }

    Optional<LearnCaseView> getSelectedCaseView()
    {
        return Optional.ofNullable(learnSetTable.getSelectionModel().getSelectedItem());
    }

    Optional<LearnSetName> getFocusedLearnSet()
    {
        return Optional.ofNullable(learnSetListView.getSelectionModel().getSelectedItem());
    }

    Optional<LearnSetName> getDisplayedLearnSet()
    {
        return Optional.ofNullable(currentLearnSetName);
    }

    public void setDisplayedLearnSet(LearnSetName aLearnSetName)
    {
        this.currentLearnSetName = aLearnSetName;
    }

    void changeFocus(LearnSetName aLearnSetName)
    {
        learnSetListView.getSelectionModel().select(aLearnSetName);
    }

    NewCaseDto showNewCaseWindow()
    {
        NewCaseWindowDisplayer windowDisplayer = new NewCaseWindowDisplayer();
        return windowDisplayer.showNewCaseWindow();
    }

    void showLearnPanel(LearnOptions aLearnOptions, List<LearnCaseView> aCases)
    {
        LearnPanelDisplayer panelDisplayer = new LearnPanelDisplayer(aLearnOptions, aCases);
        panelDisplayer.displayWindow();
    }

    Optional<LearnOptions> showLearnOptionsPanel()
    {
        LearnOptionsDisplayer learnOptionsDisplayer = new LearnOptionsDisplayer();
        return learnOptionsDisplayer.displayPanel();
    }

    void setWindowIconified(boolean isIconified)
    {
        getStage().setIconified(isIconified);
    }

    void closeApplication()
    {
        killThisWindow();
    }


}
