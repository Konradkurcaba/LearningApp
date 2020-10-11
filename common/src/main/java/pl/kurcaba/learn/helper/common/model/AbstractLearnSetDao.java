package pl.kurcaba.learn.helper.common.model;

import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.NonUniqueException;

import java.io.IOException;
import java.util.LinkedHashSet;

public abstract class AbstractLearnSetDao implements LearnSetDaoIf
{
    @Override
    public LearnSet createNewLearnSet(LearnSetName aNewName) throws IOException, NonUniqueException
    {
        if(getAllNames().contains(aNewName))
        {
            throw new NonUniqueException("Learn set name must be unique");
        }
        LearnSet newSet = new LearnSet(aNewName, new LinkedHashSet<>());
        this.saveAs(newSet);
        return newSet;
    }
}
