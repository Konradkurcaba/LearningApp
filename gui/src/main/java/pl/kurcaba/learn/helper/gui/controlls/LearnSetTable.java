package pl.kurcaba.learn.helper.gui.controlls;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import pl.kurcaba.learn.helper.gui.controller.AbstractCommand;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

public class LearnSetTable extends TableView<LearnCaseView> {


    public void initTable(AbstractCommand deleteCommand)
    {
        initColumns();
        initContextMenu(deleteCommand);

        Label emptyTable = new Label("Brak zawartości");
        setPlaceholder(emptyTable);
    }

    private void initContextMenu(AbstractCommand command) {
        ContextMenu tableMenu = new ContextMenu();
        MenuItemCommand deleteItem = new MenuItemCommand("Usuń");
        deleteItem.setCommand(command);
        tableMenu.getItems().add(deleteItem);
        contextMenuProperty().setValue(tableMenu);
    }

    private void initColumns() {
        TableColumn<LearnCaseView, String> nameColumn = new TableColumn<>("Nazwa");
        nameColumn.setCellFactory(column -> new TableCell<>()
        {
            @Override
            protected void updateItem(String item, boolean empty)
            {
                if (item != null && !empty)
                {
                    setText(item);
                } else
                {
                    setText("");
                }
            }
        });
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));

        TableColumn<LearnCaseView, String> definitionColumn = new TableColumn<>("Definicja");
        definitionColumn.setCellFactory(column -> new TableCell<>()
        {
            @Override
            protected void updateItem(String item, boolean empty)
            {
                if (item != null && !empty)
                {
                    setText(item);
                } else
                {
                    setText("");
                }
            }
        });
        definitionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDefinition()));

        TableColumn<LearnCaseView, Boolean> imageColumn = new TableColumn<>("Obraz");
        imageColumn.setCellFactory(tc -> new CheckBoxTableCell<>());

        getColumns().addAll(nameColumn, definitionColumn, imageColumn);
    }
}
