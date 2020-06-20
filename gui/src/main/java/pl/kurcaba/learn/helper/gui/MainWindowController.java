package pl.kurcaba.learn.helper.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.kurcaba.learn.helper.learnset.model.LearnDataManager;

import java.io.IOException;
import java.util.List;

public class MainWindowController
{

    private LearnDataManager dataManager;

    @FXML
    private Button newSet;

    @FXML
    private TextField name;

    @FXML
    private TextField definition;

    @FXML
    private Button image;

    @FXML
    private Button save;

    @FXML
    private ListView mainList;

    @FXML
    private TableView mainTable;

    @FXML
    private void initialize() throws IOException
    {
        refreshMainListData();
        initTable();
    }

    private void initTable()
    {
        TableColumn nameColumn = new TableColumn("Nazwa");
        TableColumn definitionColumn = new TableColumn("Definicja");
        TableColumn imageColumn = new TableColumn("Obraz");

        mainTable.getColumns().addAll(nameColumn,definitionColumn,imageColumn);
    }

    private void refreshMainListData() throws IOException {
        List<String> learnSetsNames = dataManager.getAllSetsNames();
        mainList.setItems(FXCollections.observableArrayList(learnSetsNames));
    }

    public void setDataManager(LearnDataManager dataManager) {
        this.dataManager = dataManager;
    }
}
