package pl.kurcaba.learn.helper.remote.backend;

import pl.kurcaba.learn.helper.common.model.AbstractLearnSetDao;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.model.LearnSetDaoIf;
import pl.kurcaba.learn.helper.common.model.ModelConstants;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.remote.backend.messaging.MessageQueueSender;
import pl.kurcaba.learn.helper.remote.backend.messaging.MessageTopicSender;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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

    @EJB
    private LearnSetValidator validator;

    @EJB
    private MessageQueueSender sender;

    @EJB
    private MessageTopicSender messageSender;

    @Override
    public List<LearnSetName> getAllNames()
    {
        TypedQuery<LearnSetName> allNamesQuery = entityManager.createNamedQuery(ModelConstants.GET_ALL_SET_NAMES, LearnSetName.class);
        return allNamesQuery.getResultList();
    }

    @Override
    public LearnSet getSetByName(LearnSetName aLearnSetName) throws IOException, ClassNotFoundException
    {
        TypedQuery<LearnSet> learnSetByName = entityManager.createNamedQuery(ModelConstants.GET_LEARN_SET_BY_NAME, LearnSet.class);
        learnSetByName.setParameter(1, aLearnSetName);
        sender.sendMessage(null);
        return learnSetByName.getSingleResult();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public LearnSet saveChanges(LearnSet aSetToSave) throws IOException
    {
        aSetToSave.setSaved();
        LearnSet merged = entityManager.merge(aSetToSave);
        messageSender.sendMessage(aSetToSave);
        return merged;

    }


    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public LearnSet saveAs(LearnSet aSetToSave) throws IOException
    {
        if (validator.checkIfSetIsCorrect(aSetToSave))
        {
            aSetToSave.setSaved();
            entityManager.persist(aSetToSave);
            return aSetToSave;
        } else
            throw new IllegalArgumentException("A given learn set has not been validated correctly");
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void remove(LearnSetName learnSet)
    {
        Query query = entityManager.createQuery("DELETE FROM LearnSet l WHERE l.learnSetName = ?1");
        query.setParameter(1, learnSet);
        query.executeUpdate();
    }

}
