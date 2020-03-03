package pl.kurcaba.learn.helper.learnset.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pl.kurcaba.learn.helper.persistence.PersistenceManager;

import java.io.IOException;
import java.util.List;

public class MainWindowController
{
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

    private PersistenceManager manager;

    @FXML
    private void initialize() throws IOException
    {
        manager = new PersistenceManager(null);
        List<String> learnSet = manager.getAllSetsNames();
        mainList.getItems().addAll(learnSet);
    }

}
