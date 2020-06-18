package pl.kurcaba.learn.helper.learnset.core;

import pl.kurcaba.learn.helper.learnset.model.LearnSetDto;
import pl.kurcaba.learn.helper.learnset.model.LearnSetManager;
import pl.kurcaba.learn.helper.persistence.LearnSetDaoIf;

import java.io.IOException;
import java.util.List;

public class LearnDataManager
{
    private final LearnSetDaoIf learnSetDaoIf;

    public LearnDataManager(LearnSetDaoIf learnSetDao)
    {
        this.learnSetDaoIf = learnSetDao;
    }

    public List<String> getAllSetsNames() throws IOException
    {
        return learnSetDaoIf.getAllNames();
    }

    public LearnSetManager getManager(String aLearnSetName) throws IOException
    {
        LearnSetDto learnSetDto = learnSetDaoIf.getSetByName(aLearnSetName);
        return new LearnSetManager(learnSetDto);
    }

    public LearnSetManager createNewLearnSet()
    {
        return new LearnSetManager(new LearnSetDto());
    }

    public void save(LearnSetManager aManagerToSave)
    {

    }
}
