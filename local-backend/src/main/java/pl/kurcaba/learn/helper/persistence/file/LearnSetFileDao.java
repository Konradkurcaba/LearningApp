package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.common.model.AbstractLearnSetDao;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.values.LearnSetName;

import java.io.IOException;
import java.util.List;

public class LearnSetFileDao extends AbstractLearnSetDao
{
    private final LearnSetReader fileReader;
    private final LearnSetWriter fileWriter;

    public LearnSetFileDao(LearnSetReader aReader, LearnSetWriter aWriter)
    {
        fileReader = aReader;
        fileWriter = aWriter;
    }

    @Override
    public List<LearnSetName> getAllNames() throws IOException
    {
        return fileReader.getAllNames();
    }

    @Override
    public LearnSet getSetByName(LearnSetName aSetName) throws IOException, ClassNotFoundException
    {
        return fileReader.readLearnSet(aSetName);
    }

    @Override
    public void saveChanges(LearnSet aSetToSave) throws IOException
    {
        if (!fileReader.exists(aSetToSave.getLearnSetName()))
        {
            saveAs(aSetToSave);
        }
        fileWriter.writeLearnSetToFile(aSetToSave);
        aSetToSave.setSaved();
    }

    @Override
    public void saveAs(LearnSet aSetToSave) throws IOException
    {
        if (fileReader.exists(aSetToSave.getLearnSetName()))
        {
            throw new IOException("The set cannot be saved, a set with similar filename already exists");
        }
        fileWriter.writeLearnSetToFile(aSetToSave);
        aSetToSave.setSaved();
    }

    @Override
    public void remove(LearnSetName learnSetName)
    {
        fileWriter.removeLearnSet(learnSetName);
    }




}
