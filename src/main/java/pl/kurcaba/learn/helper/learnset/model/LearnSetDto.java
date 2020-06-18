package pl.kurcaba.learn.helper.learnset.model;

import java.util.ArrayList;
import java.util.List;

public class LearnSetDto
{
    private final List<LearnCaseDto> learnSetParts = new ArrayList<>();
    public List<LearnCaseDto> getLearnSetCases()
    {
        return learnSetParts;
    }
}


