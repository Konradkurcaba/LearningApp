package pl.kurcaba.learn.helper.learnset.model;

import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class LearnSet {

    private final LearnSetName learnSetName;
    private final LinkedHashSet<LearnCase> learnCases;
    private boolean hasUnsavedChanges = false;

    public LearnSet(LearnSetName learnSetName, LinkedHashSet<LearnCase> learnSetParts) {
        this.learnSetName = learnSetName;
        this.learnCases = learnSetParts;
    }

    public List<LearnCase> getLearnSetCases() {
        return List.copyOf(learnCases);
    }

    public void addLearnCase(LearnCase aLearnCase) {
        learnCases.add(aLearnCase);
        hasUnsavedChanges = true;
    }

    public boolean removeCase(UUID aCaseId) {
        hasUnsavedChanges = true;
        return learnCases.removeIf(learnCase -> aCaseId.equals(learnCase.getId()));
    }

    public LearnCase createNewCase(String aName, String aDefinition) {
        LearnCase newCase = new LearnCase(aName, aDefinition);
        learnCases.add(newCase);
        hasUnsavedChanges = true;
        return newCase;
    }

    public LearnSetName getLearnSetName() {
        return learnSetName;
    }

    public boolean hasUnsavedChanges() {
        return hasUnsavedChanges;
    }

    public void setSaved() {
        hasUnsavedChanges = false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearnSet learnSet = (LearnSet) o;
        return hasUnsavedChanges == learnSet.hasUnsavedChanges &&
                learnSetName.equals(learnSet.learnSetName) &&
                learnCases.equals(learnSet.learnCases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(learnSetName, learnCases, hasUnsavedChanges);
    }
}


