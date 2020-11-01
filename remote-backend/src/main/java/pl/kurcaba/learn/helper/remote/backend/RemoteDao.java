package pl.kurcaba.learn.helper.remote.backend;

import pl.kurcaba.learn.helper.common.model.AbstractLearnSetDao;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.model.LearnSetDaoIf;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.NonUniqueException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;

@Stateless
public class RemoteDao extends AbstractLearnSetDao implements LearnSetDaoIf
{

    @PersistenceContext(unitName = "learnPersistenceUnit")
    private EntityManager entityManager;

    @Override
    public List<LearnSetName> getAllNames()
    {
        TypedQuery<LearnSetName> allNamesQuery
                = entityManager.createQuery(DaoConstants.ALL_NAMES_QUERY, LearnSetName.class);
        return allNamesQuery.getResultList();
    }

    @Override
    public LearnSet getSetByName(LearnSetName aLearnSetName) throws IOException, ClassNotFoundException
    {
        TypedQuery<LearnSet> learnSetByName
                = entityManager.createQuery(DaoConstants.LEARN_SET_BY_NAME, LearnSet.class);
        learnSetByName.setParameter(1,aLearnSetName);
        return learnSetByName.getSingleResult();
    }

    @Override
    public void saveChanges(LearnSet aSetToSave) throws IOException
    {
        entityManager.merge(aSetToSave);
    }

    @Override
    public void saveAs(LearnSet aSetToSave) throws IOException
    {
        entityManager.persist(aSetToSave);
    }

    @Override
    public void remove(LearnSetName learnSet)
    {
        entityManager.remove(learnSet);
    }

}
