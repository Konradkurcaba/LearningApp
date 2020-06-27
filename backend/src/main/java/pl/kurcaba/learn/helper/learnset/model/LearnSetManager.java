package pl.kurcaba.learn.helper.learnset.model;

import pl.kurcaba.learn.helper.learnset.view.LearnCaseView;
import pl.kurcaba.learn.helper.learnset.view.LearnCaseViewDirector;
import pl.kurcaba.learn.helper.learnset.controller.LearnCaseController;

import java.util.List;
import java.util.stream.Collectors;

public class LearnSetManager
{
    private final LearnSet learnSet;

    public LearnSetManager(LearnSet learnSet)
    {
        this.learnSet = learnSet;
    }

    public List<LearnCaseController> getAllControllers()
    {
        return learnSet.getLearnSetCases().stream()
                .map(learnCase -> new LearnCaseController(learnCase, new LearnCaseViewDirector(LearnCaseView.builder())))
                .collect(Collectors.toList());
    }

    public LearnCaseController createNewCase(String aName, String aDefinition)
    {
        LearnCase newCase = new LearnCase(aName, aDefinition);
        learnSet.getLearnSetCases().add(newCase);
        return new LearnCaseController(newCase, new LearnCaseViewDirector(LearnCaseView.builder()));
    }
    

    LearnSet getLearnSet() {
        return learnSet;
    }

}
