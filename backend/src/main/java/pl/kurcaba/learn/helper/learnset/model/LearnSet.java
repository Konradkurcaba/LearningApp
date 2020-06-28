package pl.kurcaba.learn.helper.learnset.model;

import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

import java.io.Serializable;
import java.util.LinkedHashSet;

public class LearnSet implements Serializable
{
    private final LearnSetName learnSetName;
    private final LinkedHashSet<LearnCase> learnSetParts;

    public LearnSet(LearnSetName learnSetName, LinkedHashSet<LearnCase> learnSetParts) {
        this.learnSetName = learnSetName;
        this.learnSetParts = learnSetParts;
    }

    public LinkedHashSet<LearnCase> getLearnSetCases()
    {
        return learnSetParts;
    }

    public LearnSetName getLearnSetName() {
        return learnSetName;
    }
}


