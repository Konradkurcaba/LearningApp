package pl.kurcaba.learn.helper.learnset.model;

import java.util.LinkedHashSet;
import java.util.UUID;

public class LearnSetManager {
    private final LearnSet learnSet;

    public LearnSetManager(LearnSet learnSet) {
        this.learnSet = learnSet;
    }

    public LinkedHashSet<LearnCase> getAllLearnCases() {
        return learnSet.getLearnSetCases();
    }

    public LearnCase createNewCase(String aName, String aDefinition) {
        LearnCase newCase = new LearnCase(aName, aDefinition);
        learnSet.getLearnSetCases().add(newCase);
        return newCase;
    }

    LearnSet getLearnSet() {
        return learnSet;
    }

    public boolean deleteCase(LearnCase view) {
        return learnSet.getLearnSetCases().removeIf(learnCase -> view.getId().equals(learnCase.getId()));
    }

    public boolean deleteCase(UUID aCaseId) {
        return learnSet.getLearnSetCases().removeIf(learnCase -> aCaseId.equals(learnCase.getId()));
    }
}
