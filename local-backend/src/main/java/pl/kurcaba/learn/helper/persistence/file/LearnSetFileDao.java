package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.common.model.AbstractLearnSetDao;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.NonUniqueException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class LearnSetFileDao extends AbstractLearnSetDao
{
    private final LearnSetReader fileReader;
    private final LearnSetWriter fileWriter;

    public LearnSetFileDao(Path aPathToDataDir)
    {
        fileReader = new LearnSetReader(aPathToDataDir);
        fileWriter = new LearnSetWriter(aPathToDataDir);
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
        fileWriter.writeLearnSetToFile(aSetToSave);
        aSetToSave.setSaved();
    }

    @Override
    public void saveAs(LearnSet aSetToSave) throws IOException, NonUniqueException
    {
        fileWriter.writeNewSetToFile(aSetToSave);
        aSetToSave.setSaved();
    }

    @Override
    public void remove(LearnSetName learnSetName) throws IOException
    {
        fileWriter.removeLearnSet(learnSetName);
    }




}
