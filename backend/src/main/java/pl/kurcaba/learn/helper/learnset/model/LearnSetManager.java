package pl.kurcaba.learn.helper.learnset.model;

import pl.kurcaba.learn.helper.learnset.view.LearnCaseView;
import pl.kurcaba.learn.helper.learnset.view.LearnCaseViewDirector;
import pl.kurcaba.learn.helper.learnset.controller.LearnCaseController;

import java.util.List;
import java.util.stream.Collectors;

public class LearnSetManager
{
    private final LearnSet learnSet;
    private boolean isSetSaved;

    public LearnSetManager(LearnSet learnSet, boolean isSaved)
    {
        this.learnSet = learnSet;
        isSetSaved = isSaved;
    }

    public List<LearnCaseController> getAllControllers()
    {
        return learnSet.getLearnSetCases().stream()
                .map(learnCase -> new LearnCaseController(learnCase, new LearnCaseViewDirector(LearnCaseView.builder())))
                .collect(Collectors.toList());
    }

    public LearnCaseController createNewCase(String aName, String aDefinition)
    {
        return null;
    }

    LearnSet getLearnSet() {
        return learnSet;
    }

    public boolean isSetSaved() {
        return isSetSaved;
    }

    public void setSetSaved(boolean setSaved) {
        isSetSaved = setSaved;
    }
}
