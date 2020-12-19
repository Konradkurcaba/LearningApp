package pl.kurcaba.learn.helper.gui.dialogs.addcase;

import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowDisplayer;

import java.util.Optional;

public class NewCaseWindowDisplayer extends AbstractWindowDisplayer<AddCaseWindowController> {

    public static final String NEW_CASE_FXML = "fxml/add_new_case_panel.fxml";

    public NewCaseWindowDisplayer() {
    }

    @Override
    protected AddCaseWindowController createController() {
        return new AddCaseWindowController();
    }

    public Optional<NewCaseDto> showNewCaseWindow() {

        Stage stage = prepareStage(NEW_CASE_FXML);
        stage.showAndWait();
        return getController().getCreatedCase();
    }
}
