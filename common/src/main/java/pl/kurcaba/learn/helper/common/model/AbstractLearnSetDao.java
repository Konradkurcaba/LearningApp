package pl.kurcaba.learn.helper.common.model;

import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.NonUniqueException;

import java.io.IOException;
import java.util.TreeSet;

public abstract class AbstractLearnSetDao implements LearnSetDaoIf
{
    @Override
    public LearnSet createNewLearnSet(LearnSetName aNewName) throws IOException, NonUniqueException
    {
        LearnSet newSet = new LearnSet(aNewName, new TreeSet<>());
        this.saveAs(newSet);
        return newSet;
    }
}
