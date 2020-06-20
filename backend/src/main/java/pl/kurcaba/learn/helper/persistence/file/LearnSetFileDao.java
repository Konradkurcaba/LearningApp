package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.learnset.model.LearnSet;
import pl.kurcaba.learn.helper.persistence.LearnSetDaoIf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

class LearnSetFileDao implements LearnSetDaoIf
{
    public static String FILE_EXTENSION = "lap";

    private final LearnSetReader fileReader;
    private final FileObjectWriter fileWriter;
    private final Path mainDirectoryPath;

    public LearnSetFileDao(Path aMainDirectoryPath, LearnSetReader aReader, FileObjectWriter aWriter)
    {
        fileReader = aReader;
        fileWriter = aWriter;
        mainDirectoryPath = aMainDirectoryPath;
    }

    @Override
    public List<String> getAllNames() throws IOException
    {
        return fileReader.getAllNames(mainDirectoryPath);
    }

    @Override
    public LearnSet getSetByName(String aSetName) throws IOException, ClassNotFoundException
    {
        return fileReader.readLearnSet(getFile(aSetName));
    }

    @Override
    public void saveChanges(LearnSet aSetToSave) throws IOException
    {
        File aFileToSave = getFile(aSetToSave.getLearnSetName());
        if(!aFileToSave.exists())
        {
            saveAs(aSetToSave);
        }
        FileObjectWriter objectWriter = new FileObjectWriter(aFileToSave);
        objectWriter.writeObjectToFile(aFileToSave);
    }

    @Override
    public void saveAs(LearnSet aSetToSave) throws IOException {
        File aFileToSave = getFile(aSetToSave.getLearnSetName());
        if(aFileToSave.exists())
        {
            throw new IOException("The set cannot be saved, a set with similar filename already exists");
        }
        fileWriter.writeObjectToFile(aFileToSave);
    }


    private File getFile(String aLearnSetName)
    {
        String fileName = aLearnSetName + "." + FILE_EXTENSION;
        Path pathToOriginFile = Path.of(mainDirectoryPath.toString(), fileName);
        return pathToOriginFile.toFile();
    }


}
