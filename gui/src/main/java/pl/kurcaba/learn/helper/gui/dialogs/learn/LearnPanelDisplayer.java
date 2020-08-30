package pl.kurcaba.learn.helper.gui.dialogs.learn;

import pl.kurcaba.learn.helper.gui.controller.AbstractWindowDisplayer;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.util.List;

public class LearnPanelDisplayer extends AbstractWindowDisplayer<LearnPanelController> {

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

    @Override
    protected LearnPanelController createController() {
        return new LearnPanelController(isNameDisplayed, isDefinitionDisplayed, isImageDisplayed
                , learnCases);
    }
}
