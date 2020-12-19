package pl.kurcaba.learn.helper.gui.controlls;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import pl.kurcaba.learn.helper.gui.controller.main.DeleteCaseCommand;
import pl.kurcaba.learn.helper.gui.controller.main.EditCaseCommand;
import pl.kurcaba.learn.helper.gui.core.ApplicationConstants;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

public class LearnSetTable extends TableView<LearnCaseView> {


    public void initTable(DeleteCaseCommand aDeleteCmd, EditCaseCommand aEditCmd)
    {
        initColumns();
        initContextMenu(aEditCmd,aDeleteCmd);

        ResourceBundle textBundle = ListResourceBundle.getBundle(ApplicationConstants.DEFAULT_BUNDLE_NAME);
        Label emptyTable = new Label(textBundle.getString("noDataToDisplay"));
        setPlaceholder(emptyTable);
    }

    private void initContextMenu(EditCaseCommand editCaseCommand, DeleteCaseCommand aDeleteCmd)
    {
        ContextMenu tableMenu = new ContextMenu();

        ResourceBundle textBundle = ResourceBundle.getBundle(ApplicationConstants.DEFAULT_BUNDLE_NAME);
        MenuItemCommand deleteItem = new MenuItemCommand(textBundle.getString("deleteAction"));
        deleteItem.setCommand(aDeleteCmd);
        tableMenu.getItems().add(deleteItem);

        MenuItemCommand editItemCommand = new MenuItemCommand(textBundle.getString("editCaseAction"));
        editItemCommand.setCommand(editCaseCommand);
        tableMenu.getItems().add(editItemCommand);

        contextMenuProperty().setValue(tableMenu);
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

        imageColumn.setCellFactory(column -> {
            StringTableCell cell =  new StringTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        imageColumn.setCellValueFactory(tc -> {
            String textInCell;
            boolean hasAnyImage = !tc.getValue().getImages().isEmpty();
            if(hasAnyImage)
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
