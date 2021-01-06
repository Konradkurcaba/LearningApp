package pl.kurcaba.learn.helper.common.model;

import pl.kurcaba.learn.helper.common.values.LearnSetName;

import javax.persistence.*;
import java.util.List;
import java.util.SortedSet;
import java.util.UUID;
import java.util.*;
import java.util.stream.Collectors;

@NamedQueries(
        {
            @NamedQuery(name = ModelConstants.GET_LEARN_SET_BY_NAME, query = "SELECT l FROM LearnSet l WHERE l.learnSetName = ?1"),
            @NamedQuery(name = ModelConstants.GET_ALL_SET_NAMES, query = "SELECT s.learnSetName FROM LearnSet s")
        })
@Entity
public class LearnSet extends BaseEntity
{
    @Embedded
    @Column(unique = true)
    private LearnSetName learnSetName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "parentLearnSet")
    @OrderBy("createDate ASC")
    private SortedSet<LearnCase> learnCases;
    private boolean hasUnsavedChanges = false;

    public LearnSet(LearnSetName learnSetName, SortedSet<LearnCase> learnSetParts)
    {
        this.learnSetName = learnSetName;
        this.learnCases = learnSetParts;
    }

    /**
     * Copying constructor, creates a deep clone of given Learn set.
     */
    public LearnSet(LearnSet aLearnSet)
    {
        learnSetName = aLearnSet.learnSetName;
        hasUnsavedChanges = aLearnSet.hasUnsavedChanges;
        learnCases = aLearnSet.learnCases.stream()
                .map(LearnCase::new)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public LearnSet()
    {
    }

    public List<LearnCase> getLearnSetCases()
    {
        return List.copyOf(learnCases);
    }


    public boolean removeCase(UUID aCaseId)
    {
        hasUnsavedChanges = true;
        return learnCases.removeIf(learnCase -> aCaseId.toString().equals(learnCase.getUuid()));
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

    public void editCase(UUID aId, String aName, String aDefinition, List<byte[]> aImages)
    {
        LearnCase foundCase = learnCases.stream()
                .filter(learnCase -> learnCase.getUuid().equals(aId.toString()))
                .findAny()
                .orElseThrow(() -> {throw new IllegalArgumentException("werewr");});

        foundCase.setName(aName);
        foundCase.setDefinition(aDefinition);
        foundCase.setImages(aImages);
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
        return hasUnsavedChanges == learnSet.hasUnsavedChanges &&
                learnSetName.equals(learnSet.learnSetName) &&
                Objects.equals(learnCases, learnSet.learnCases);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(learnSetName);
    }
}


