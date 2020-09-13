package pl.kurcaba.learn.helper.gui.dialogs.options;

import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowDisplayer;

import java.util.Optional;

public class LearnOptionsDisplayer extends AbstractWindowDisplayer<LearnOptionsController> {

    private final String LEARN_OPTIONS_FXML = "fxml/learn_options_dialog.fxml";

    @Override
    protected LearnOptionsController createController() {
        return new LearnOptionsController();
    }

    public Optional<LearnOptions> displayPanel() {
        Stage stage = prepareStage(LEARN_OPTIONS_FXML);
        stage.showAndWait();
        return getController().getLearnOptions();
    }
}
