package pl.kurcaba.learn.helper.gui.dialogs.learn;

import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowDisplayer;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;
import java.nio.file.Path;

import java.util.List;

public class LearnPanelDisplayer extends AbstractWindowDisplayer<LearnPanelController> {

    public static final Path FXML = Path.of("fxml/learn_panel.fxml");

    private final boolean isNameDisplayed;
    private final boolean isDefinitionDisplayed;
    private final boolean isImageDisplayed;
    private final List<LearnCaseView> learnCases;

    public LearnPanelDisplayer(boolean isNameDisplayed, boolean isDefinitionDisplayed
            , boolean isImageDisplayed, List<LearnCaseView> learnCases) {
        this.isNameDisplayed = isNameDisplayed;
        this.isDefinitionDisplayed = isDefinitionDisplayed;
        this.isImageDisplayed = isImageDisplayed;
        this.learnCases = learnCases;
    }

    public void displayWindow()
    {
        Stage stage = prepareStage(FXML);
        stage.showAndWait();
    }

    @Override
    protected LearnPanelController createController() {
        return new LearnPanelController(isNameDisplayed, isDefinitionDisplayed, isImageDisplayed
                , learnCases);
    }
}
