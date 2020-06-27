package pl.kurcaba.learn.helper.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import pl.kurcaba.learn.helper.learnset.controller.LearnCaseController;
import pl.kurcaba.learn.helper.learnset.model.LearnDataManager;
import pl.kurcaba.learn.helper.learnset.model.LearnSetManager;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.learnset.values.NonUniqueException;
import pl.kurcaba.learn.helper.learnset.view.LearnCaseView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainWindowController
{

    private LearnDataManager dataManager;
    private LearnSetManager setManager;
    private List<LearnCaseController> caseControllers;

    @FXML
    private Button newSet;

    @FXML
    private TextField name;

    @FXML
    private TextField definition;

    @FXML
    private Button image;

    @FXML
    private Button addCase;

    @FXML
    private Button saveSet;


    @FXML
    private ListView<LearnSetName> mainList;

    @FXML
    private TableView<LearnCaseView> mainTable;

    @FXML
    private void initialize() throws IOException
    {
        initMainList();
        refreshMainListData();
        initTable();
        initButtons();
    }

    private void initButtons()
    {
        newSet.setOnMouseClicked( event ->{
            String newSetName = displayInputDialog("Nowy zestaw", "Nazwa nowego zestawu:");
            try {
                setManager = dataManager.createNewLearnSet(new LearnSetName(newSetName));
                refreshMainListData();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NonUniqueException e) {
                e.printStackTrace();
            }
        });
        addCase.setOnMouseClicked(event ->  {
            String newCaseName = name.getText();
            String newDefinition = definition.getText();
            LearnCaseController controller = setManager.createNewCase(newCaseName,newDefinition);
            caseControllers.add(controller);
            refreshTableData();
        });

        saveSet.setOnMouseClicked(event -> {
            try {
                dataManager.save(setManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void initTable()
    {
        TableColumn<LearnCaseView, String> nameColumn = new TableColumn<>("Nazwa");
        nameColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                if (item != null && !empty) {
                    setText(item);
                } else {
                    setText("");
                }
            }
        });
        nameColumn.setCellValueFactory( param -> new SimpleStringProperty(param.getValue().getName()));

        TableColumn<LearnCaseView, String> definitionColumn = new TableColumn<>("Definicja");
        definitionColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                if (item != null && !empty) {
                    setText(item);
                } else {
                    setText("");
                }
            }
        });
        definitionColumn.setCellValueFactory( param -> new SimpleStringProperty(param.getValue().getDefinition()));

        TableColumn<LearnCaseView, Boolean> imageColumn = new TableColumn<>("Obraz");
        imageColumn.setCellFactory(tc -> new CheckBoxTableCell<>());

        mainTable.getColumns().addAll(nameColumn,definitionColumn,imageColumn);
    }


    private void refreshTableData() {
        mainTable.getItems().clear();
        mainTable.getItems().addAll(caseControllers
                .stream()
                .map(LearnCaseController::getLearnCaseView)
                .collect(Collectors.toList())
        );
    }

    private String displayInputDialog(String aDialogName, String aTitle)
    {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(aTitle);
        dialog.setContentText(aDialogName);
        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    private void initMainList()
    {
        mainList.getSelectionModel().selectedItemProperty().addListener(observable -> {
            LearnSetName focusedName = mainList.getSelectionModel().getSelectedItem();
            try {
                setManager = dataManager.getManager(focusedName);
                caseControllers = setManager.getAllControllers();
                refreshTableData();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void refreshMainListData() throws IOException {
        List<LearnSetName> learnSetsNames = dataManager.getAllSetsNames();
        mainList.setItems(FXCollections.observableArrayList(learnSetsNames));
    }

    public void setDataManager(LearnDataManager dataManager) {
        this.dataManager = dataManager;
    }
}
