package pl.kurcaba.learn.helper.gui.controlls;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

        addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event ->
                showImageItem.setDisable(!aShowImageCmd.canBeExecuted()));
    }


    private void initColumns()
    {
        TableColumn<LearnCaseView, String> nameColumn = createNameColumn();
        TableColumn<LearnCaseView, String> definitionColumn = createDefinitionColumn();
        TableColumn<LearnCaseView, String> imageColumn = createImageColumn();

        TableColumn<LearnCaseView, Boolean> isUsedToLearn = new TableColumn<>("Status");
        isUsedToLearn.setCellValueFactory(tc -> tc.getValue().isUsedToLearnProperty());
        isUsedToLearn.setCellFactory(CheckBoxTableCell.forTableColumn(isUsedToLearn));
        isUsedToLearn.setPrefWidth(200);
        isUsedToLearn.editableProperty().setValue(true);

        getColumns().addAll(nameColumn, definitionColumn, imageColumn, isUsedToLearn);
    }

    private TableColumn<LearnCaseView, String> createImageColumn() {
        TableColumn<LearnCaseView, String> imageColumn = new TableColumn<>("Obraz");
        imageColumn.setCellFactory(column -> new StringTableCell<>());
        imageColumn.setCellValueFactory(tc -> {
            String textInCell;
            if(tc.getValue().getImage().isPresent())
            {
                textInCell = "Tak";
            }
            else
            {
                textInCell = "Nie";
            }
            return new SimpleStringProperty(textInCell);
        });
        imageColumn.setPrefWidth(200);
        return imageColumn;
    }

    private TableColumn<LearnCaseView, String> createDefinitionColumn() {
        TableColumn<LearnCaseView, String> definitionColumn = new TableColumn<>("Definicja");
        definitionColumn.setCellFactory(column -> new StringTableCell<>());
        definitionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDefinition()));
        definitionColumn.setPrefWidth(200);
        return definitionColumn;
    }

    private TableColumn<LearnCaseView, String> createNameColumn() {
        TableColumn<LearnCaseView, String> nameColumn = new TableColumn<>("Nazwa");
        nameColumn.setCellFactory(column -> new StringTableCell<>());
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        nameColumn.setPrefWidth(200);
        return nameColumn;
    }


}
