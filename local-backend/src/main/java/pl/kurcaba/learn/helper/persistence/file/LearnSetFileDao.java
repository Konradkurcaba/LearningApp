package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.common.model.AbstractLearnSetDao;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.values.LearnSetName;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class LearnSetFileDao extends AbstractLearnSetDao
{
    private final LearnSetReader fileReader;
    private final FileObjectWriter fileWriter;

    public LearnSetFileDao(LearnSetReader aReader, FileObjectWriter aWriter)
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
//        return learnSetDto.toLearnSet();
    }

    @Override
    public void saveChanges(LearnSet aSetToSave) throws IOException
    {
        if (!fileReader.exists(aSetToSave.getLearnSetName()))
        {
            saveAs(aSetToSave);
        }
        LearnSetDto dtoToSave = new LearnSetDto(aSetToSave);
        fileWriter.writeObjectToFile(dtoToSave, aSetToSave.getLearnSetName());
        aSetToSave.setSaved();
    }

    @Override
    public void saveAs(LearnSet aSetToSave) throws IOException
    {
        if (fileReader.exists(aSetToSave.getLearnSetName()))
        {
            throw new IOException("The set cannot be saved, a set with similar filename already exists");
        }
        LearnSetDto dtoToSave = new LearnSetDto(aSetToSave);
        fileWriter.writeObjectToFile(dtoToSave, aSetToSave.getLearnSetName());
        aSetToSave.setSaved();
    }

    @Override
    public void remove(LearnSetName learnSetName)
    {
        fileWriter.removeLearnSet(learnSetName);
    }


//    private File getFile(LearnSetName aLearnSetName)
//    {
//        String fileName = aLearnSetName.toString() + "." + FILE_EXTENSION;
//        Path pathToOriginFile = Path.of(mainDirectoryPath.toString(), fileName);
//        return pathToOriginFile.toFile();
//    }


}
