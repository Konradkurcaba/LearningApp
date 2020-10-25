package pl.kurcaba.learn.helper.remote.backend;

import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.model.LearnSetDaoIf;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.NonUniqueException;

import javax.ejb.Stateless;
import java.io.IOException;
import java.util.List;

@Stateless
public class RemoteDao implements LearnSetDaoIf
{

//    @PersistenceContext(unitName = "learnPersistenceUnit")
//    private EntityManager entityManager;

    @Override
    public List<LearnSetName> getAllNames() throws IOException
    {
//        String getAllNamesQuery = "SELECT s.learnSetName FROM LearnSet s";
//        Query allNamesQuery = entityManager.createQuery(getAllNamesQuery);
//        return allNamesQuery.getResultList();
        return null;
    }

    @Override
    public LearnSet getSetByName(LearnSetName aLearnSetName) throws IOException, ClassNotFoundException
    {
        return null;
    }

    @Override
    public void saveChanges(LearnSet aSetToSave) throws IOException
    {

    }

    @Override
    public void saveAs(LearnSet aSetToSave) throws IOException
    {

    }

    @Override
    public void remove(LearnSetName learnSet)
    {

    }

    @Override
    public LearnSet createNewLearnSet(LearnSetName aNewName) throws IOException, NonUniqueException
    {
        return null;
    }
}
