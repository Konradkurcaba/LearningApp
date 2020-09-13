package pl.kurcaba.learn.helper.gui.dialogs.learn;

import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowDisplayer;
import pl.kurcaba.learn.helper.gui.dialogs.options.LearnOptions;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.util.List;

public class LearnPanelDisplayer extends AbstractWindowDisplayer<LearnPanelController> {

    public static final String FXML = "fxml/learn_panel.fxml";

    private final LearnOptions learnOptions;
    private final List<LearnCaseView> learnCases;

    public LearnPanelDisplayer(LearnOptions aLearnOptions, List<LearnCaseView> aLearnCases) {
        this.learnOptions = aLearnOptions;
        this.learnCases = aLearnCases;
    }

    public void displayWindow() {
        Stage stage = prepareStage(FXML);
        stage.showAndWait();
    }

    @Override
    protected LearnPanelController createController() {
        return new LearnPanelController(learnOptions, learnCases);
    }
}
