package pl.kurcaba.learn.helper.gui.controlls;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.ContextMenuEvent;
import pl.kurcaba.learn.helper.gui.controller.main.DeleteCaseCommand;
import pl.kurcaba.learn.helper.gui.controller.main.ShowImageCommand;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

public class LearnSetTable extends TableView<LearnCaseView> {


    public void initTable(DeleteCaseCommand aDeleteCmd, ShowImageCommand aShowImageCmd)
    {
        initColumns();
        initContextMenu(aDeleteCmd, aShowImageCmd);

        Label emptyTable = new Label("Brak danych do wyświetlenia");
        setPlaceholder(emptyTable);
    }

    private void initContextMenu(DeleteCaseCommand aDeleteCmd, ShowImageCommand aShowImageCmd) {
        ContextMenu tableMenu = new ContextMenu();
        MenuItemCommand deleteItem = new MenuItemCommand("Usuń");
        deleteItem.setCommand(aDeleteCmd);
        tableMenu.getItems().add(deleteItem);

        MenuItemCommand showImageItem = new MenuItemCommand("Pokaż obraz");
        showImageItem.setCommand(aShowImageCmd);
        tableMenu.getItems().add(showImageItem);

        contextMenuProperty().setValue(tableMenu);

        addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
            LearnCaseView caseView = getSelectionModel().getSelectedItem();
            showImageItem.setDisable(caseView.getImage().isEmpty());
        });

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
        nameColumn.setPrefWidth(200);

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
        definitionColumn.setPrefWidth(200);

        TableColumn<LearnCaseView, Boolean> imageColumn = new TableColumn<>("Obraz");
        imageColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
        imageColumn.setCellValueFactory(tc -> new SimpleBooleanProperty(tc.getValue().getImage().isPresent()));
        imageColumn.setPrefWidth(200);

        getColumns().addAll(nameColumn, definitionColumn, imageColumn);
    }


}
