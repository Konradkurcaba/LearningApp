package pl.kurcaba.learn.helper.learnset.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.learnset.values.LearnSetNameFormatException;
import pl.kurcaba.learn.helper.learnset.values.NonUniqueException;
import pl.kurcaba.learn.helper.persistence.LearnSetDaoIf;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;


class LearnDataManagerTest
{

    private final LearnSetName testName;
    private final LearnSet exampleSet;
    private final LearnSetDaoIf testDao;
    private final List<LearnSetName> names;

    LearnDataManagerTest() throws LearnSetNameFormatException
    {
        testName = new LearnSetName("testLearnTestName");
        exampleSet = new LearnSet(testName, new LinkedHashSet<>());
        testDao = Mockito.mock(LearnSetDaoIf.class);
        names = Arrays.asList(testName);
    }




}