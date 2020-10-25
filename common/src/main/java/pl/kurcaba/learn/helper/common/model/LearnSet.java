package pl.kurcaba.learn.helper.common.model;


import pl.kurcaba.learn.helper.common.values.LearnSetName;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.List;
import java.util.SortedSet;
import java.util.UUID;

@Entity
public class LearnSet extends BaseEntity
{
    private final LearnSetName learnSetName;

    @OneToMany(mappedBy = "parentLearnSet")
    @OrderBy("createDate ASC")
    private final SortedSet<LearnCase> learnCases;
    private boolean hasUnsavedChanges = false;

    public LearnSet(LearnSetName learnSetName, SortedSet<LearnCase> learnSetParts)
    {
        this.learnSetName = learnSetName;
        this.learnCases = learnSetParts;
    }

    public List<LearnCase> getLearnSetCases()
    {
        return List.copyOf(learnCases);
    }

    public void addLearnCase(LearnCase aLearnCase)
    {
        learnCases.add(aLearnCase);
        hasUnsavedChanges = true;
    }

    public boolean removeCase(UUID aCaseId)
    {
        hasUnsavedChanges = true;
        return learnCases.removeIf(learnCase -> aCaseId.toString().equals(learnCase.getUuid()));
    }

    public LearnCase createNewCase(String aName, String aDefinition)
    {
        LearnCase newCase = new LearnCase(aName, aDefinition);
        learnCases.add(newCase);
        hasUnsavedChanges = true;
        return newCase;
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

}


