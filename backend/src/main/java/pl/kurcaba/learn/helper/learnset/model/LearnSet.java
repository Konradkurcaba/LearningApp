package pl.kurcaba.learn.helper.learnset.model;

import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

import java.io.Serializable;
import java.util.List;

public class LearnSet implements Serializable
{
    private final LearnSetName learnSetName;
    private final List<LearnCase> learnSetParts;

    public LearnSet(LearnSetName learnSetName, List<LearnCase> learnSetParts) {
        this.learnSetName = learnSetName;
        this.learnSetParts = learnSetParts;
    }

    public List<LearnCase> getLearnSetCases()
    {
        return learnSetParts;
    }

    public LearnSetName getLearnSetName() {
        return learnSetName;
    }
}


