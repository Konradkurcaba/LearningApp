package pl.kurcaba.learn.helper.learnset.model;

import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.persistence.LearnSetDaoIf;

import java.io.IOException;
import java.util.ArrayList;
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
        return new LearnSetManager(learnSet,true);
    }

    public LearnSetManager createNewLearnSet(LearnSetName aNewName) {
        LearnSet newSet = new LearnSet(aNewName, new ArrayList<>());
        return new LearnSetManager(newSet,false);
    }

    public void save(LearnSetManager aManagerToSave) throws IOException {
        if(aManagerToSave.isSetSaved())
        {
            learnSetDao.saveAs(aManagerToSave.getLearnSet());
        }
        else
        {
            learnSetDao.saveChanges(aManagerToSave.getLearnSet());
        }
    }
}
