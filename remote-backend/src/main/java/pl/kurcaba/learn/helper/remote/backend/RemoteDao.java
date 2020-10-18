package pl.kurcaba.learn.helper.remote.backend;

import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.model.LearnSetDaoIf;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.LearnSetNameFormatException;
import pl.kurcaba.learn.helper.common.values.NonUniqueException;

import javax.ejb.Stateless;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class RemoteDao implements LearnSetDaoIf
{
    @Override
    public List<LearnSetName> getAllNames() throws IOException
    {
        try
        {
            LearnSetName name = new LearnSetName("exampleName");
            List<LearnSetName> names = new ArrayList<>();
            names.add(name);
            return names;
        } catch (LearnSetNameFormatException e)
        {
            e.printStackTrace();
            return null;
        }
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
