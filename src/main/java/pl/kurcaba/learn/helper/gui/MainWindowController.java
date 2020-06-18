package pl.kurcaba.learn.helper.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

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

    @FXML
    private void initialize() throws IOException
    {

    }

}
