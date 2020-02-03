package pl.kurcaba.learn.helper.learnset.model;

import java.util.ArrayList;
import java.util.List;

public class LearnSetModel
{
    private final List<LearnCase> learnSetParts = new ArrayList<>();

    public List<LearnCase> getLearnSetCases()
    {
        return learnSetParts;
    }

}


