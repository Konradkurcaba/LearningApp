package pl.kurcaba.learn.helper.learnset.model;

import java.util.ArrayList;
import java.util.List;

public class LearnSetModel
{
    private final List<LearnCaseModel> learnSetParts = new ArrayList<>();

    public List<LearnCaseModel> getLearnSetCases()
    {
        return learnSetParts;
    }

}


