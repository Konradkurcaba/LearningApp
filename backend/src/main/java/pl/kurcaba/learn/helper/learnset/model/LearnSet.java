package pl.kurcaba.learn.helper.learnset.model;

import java.io.Serializable;
import java.util.List;

public class LearnSet implements Serializable
{
    private final String learnSetName;
    private final List<LearnCase> learnSetParts;

    public LearnSet(String learnSetName, List<LearnCase> learnSetParts) {
        this.learnSetName = learnSetName;
        this.learnSetParts = learnSetParts;
    }

    public List<LearnCase> getLearnSetCases()
    {
        return learnSetParts;
    }

    public String getLearnSetName() {
        return learnSetName;
    }
}


