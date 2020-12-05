package pl.kurcaba.learn.helper.common.model;


import pl.kurcaba.learn.helper.common.values.LearnSetName;

import java.util.*;

public class LearnSet
{

    private final LearnSetName learnSetName;
    private final LinkedHashSet<LearnCase> learnCases;
    private boolean hasUnsavedChanges = false;

    public LearnSet(LearnSetName learnSetName, LinkedHashSet<LearnCase> learnSetParts)
    {
        this.learnSetName = learnSetName;
        this.learnCases = learnSetParts;
    }

    public List<LearnCase> getLearnSetCases()
    {
        return List.copyOf(learnCases);
    }


    public boolean removeCase(UUID aCaseId)
    {
        hasUnsavedChanges = true;
        return learnCases.removeIf(learnCase -> aCaseId.equals(learnCase.getId()));
    }

    public void addLearnCase(LearnCase aCase)
    {
        hasUnsavedChanges = true;
        learnCases.add(aCase);
    }

    public LearnCase createNewCase(String aName, String aDefinition)
    {
        LearnCase newCase = new LearnCase(aName, aDefinition);
        learnCases.add(newCase);
        hasUnsavedChanges = true;
        return newCase;
    }

    public void editCase(UUID aId, String aName, String aDefinition, byte[] aImage)
    {
        LearnCase foundCase = learnCases.stream()
                .filter(learnCase -> learnCase.getId().equals(aId))
                .findAny()
                .orElseThrow(() -> {throw new IllegalArgumentException("werewr");});

       foundCase.setName(aName);
       foundCase.setDefinition(aDefinition);
       foundCase.setImage(aImage);
       hasUnsavedChanges = true;
    }

    public LearnSetName getLearnSetName()
    {
        return learnSetName;
    }

    public boolean hasUnsavedChanges()
    {
        return hasUnsavedChanges;
    }

    public void setSaved()
    {
        hasUnsavedChanges = false;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearnSet learnSet = (LearnSet) o;
        return learnSetName.equals(learnSet.learnSetName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(learnSetName);
    }
}


