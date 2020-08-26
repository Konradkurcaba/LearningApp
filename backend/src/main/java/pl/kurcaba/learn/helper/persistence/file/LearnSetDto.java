package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.learnset.model.LearnSet;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class LearnSetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private LearnSetName learnSetName;
    private LinkedHashSet<LearnCaseDto> learnCases;

    public LearnSetDto(LearnSet learnSet) {
        this.learnSetName = learnSet.getLearnSetName();
        learnCases = learnSet.getLearnSetCases().stream()
                .map(LearnCaseDto::new).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LearnSet toLearnSet()
    {
        return new LearnSet(learnSetName, learnCases.stream()
                .map(LearnCaseDto::toLearnCase)
                .collect(Collectors.toCollection(LinkedHashSet::new)));
    }
}
