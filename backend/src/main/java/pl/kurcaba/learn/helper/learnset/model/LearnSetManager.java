package pl.kurcaba.learn.helper.learnset.model;

import pl.kurcaba.learn.helper.learnset.view.LearnCaseView;
import pl.kurcaba.learn.helper.learnset.view.LearnCaseViewDirector;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class LearnSetManager
{
    private final LearnSet learnSet;

    public LearnSetManager(LearnSet learnSet)
    {
        this.learnSet = learnSet;
    }

    public LinkedHashSet<LearnCaseView> getAllControllers()
    {
        return learnSet.getLearnSetCases().stream()
                .map(controller -> new LearnCaseViewDirector().buildFromDto(controller))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LearnCaseView createNewCase(String aName, String aDefinition)
    {
        LearnCase newCase = new LearnCase(aName, aDefinition);
        learnSet.getLearnSetCases().add(newCase);
        return new LearnCaseViewDirector().buildFromDto(newCase);
    }


    LearnSet getLearnSet() {
        return learnSet;
    }

    public boolean deleteCase(LearnCaseView view) {
        return learnSet.getLearnSetCases().removeIf( learnCase -> view.getId().equals(learnCase.getId()));
    }
}
