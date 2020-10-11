package pl.kurcaba.learn.helper.learnset.model;

import org.mockito.Mockito;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.model.LearnSetDaoIf;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.LearnSetNameFormatException;

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