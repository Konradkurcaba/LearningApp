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

    @Test
    public void shouldCorrectlyReturnLearnSetByName() throws IOException, ClassNotFoundException
    {
        //set up a test
        Mockito.when(testDao.getSetByName(testName)).thenReturn(exampleSet);
        LearnDataManager manager = new LearnDataManager(testDao);

        //real test
        LearnSet resultSet = manager.getLearnSet(testName);

        //Assertion
        Assertions.assertEquals(exampleSet, resultSet);
    }


    @Test
    public void shouldThrowExceptionWhenLearnSetNameIsNotUnique() throws IOException
    {
        //set up a test
        LearnDataManager manager = new LearnDataManager(testDao);
        Mockito.when(testDao.getAllNames()).thenReturn(names);

        //a real test
        Assertions.assertThrows(NonUniqueException.class, () -> manager.createNewLearnSet(testName));
    }

    @Test
    public void shouldCreateNewLearnSetWhenNameIsUnique() throws IOException, LearnSetNameFormatException, NonUniqueException
    {
        //set up a test
        Mockito.when(testDao.getAllNames()).thenReturn(names);
        LearnDataManager manager = new LearnDataManager(testDao);
        LearnSetName newName = new LearnSetName("exampleUniqueName");

        //a real test
        LearnSet result = manager.createNewLearnSet(newName);

        //Assertions
        Assertions.assertEquals(newName, result.getLearnSetName());
    }

    @Test
    public void newCreateSetShouldBeSaved() throws IOException, LearnSetNameFormatException, NonUniqueException
    {
        //set up a test
        Mockito.when(testDao.getAllNames()).thenReturn(names);
        LearnDataManager manager = new LearnDataManager(testDao);
        LearnSetName newName = new LearnSetName("exampleUniqueName");

        //a real test
        manager.createNewLearnSet(newName);

        //Assertions
        ArgumentCaptor<LearnSet> learnSetCaptor = ArgumentCaptor.forClass(LearnSet.class);
        Mockito.verify(testDao).saveAs(learnSetCaptor.capture());
        Assertions.assertEquals(newName, learnSetCaptor.getValue().getLearnSetName());
    }
}