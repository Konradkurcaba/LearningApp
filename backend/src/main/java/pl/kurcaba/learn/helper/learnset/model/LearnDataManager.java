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

    public LearnSetManager getManager(LearnSetName aLearnSetName) throws IOException, ClassNotFoundException {
        LearnSet learnSet = learnSetDao.getSetByName(aLearnSetName);
        return new LearnSetManager(learnSet);
    }

    public LearnSetManager createNewLearnSet(LearnSetName aNewName) throws IOException, NonUniqueException {
        if(learnSetDao.getAllNames().contains(aNewName))
        {
            throw new NonUniqueException("Learn set name must be unique");
        }
        LearnSet newSet = new LearnSet(aNewName, new LinkedHashSet<>());
        learnSetDao.saveAs(newSet);
        return new LearnSetManager(newSet);
    }

    public void save(LearnSetManager aManagerToSave) throws IOException {
        learnSetDao.saveChanges(aManagerToSave.getLearnSet());
    }
}
