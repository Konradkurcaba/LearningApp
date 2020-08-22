package pl.kurcaba.learn.helper.learnset.model;

import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.learnset.values.NonUniqueException;
import pl.kurcaba.learn.helper.persistence.LearnSetDaoIf;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

public class LearnDataManager
{
    private final LearnSetDaoIf learnSetDao;

    public LearnDataManager(LearnSetDaoIf learnSetDao)
    {
        this.learnSetDao = learnSetDao;
    }

    public List<LearnSetName> getAllSetsNames() throws IOException
    {
        return learnSetDao.getAllNames();
    }

    public LearnSet getLearnSet(LearnSetName aLearnSetName) throws IOException, ClassNotFoundException {
        return learnSetDao.getSetByName(aLearnSetName);
    }

    public LearnSet createNewLearnSet(LearnSetName aNewName) throws IOException, NonUniqueException {
        if(learnSetDao.getAllNames().contains(aNewName))
        {
            throw new NonUniqueException("Learn set name must be unique");
        }
        LearnSet newSet = new LearnSet(aNewName, new LinkedHashSet<>());
        learnSetDao.saveAs(newSet);
        return newSet;
    }

    public void save(LearnSet aSetToSave) throws IOException {
        learnSetDao.saveChanges(aSetToSave);
    }
}
