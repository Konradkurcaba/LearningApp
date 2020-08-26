package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.learnset.model.LearnSet;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.persistence.LearnSetDaoIf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class LearnSetFileDao implements LearnSetDaoIf
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
    public List<LearnSetName> getAllNames() throws IOException
    {
        return fileReader.getAllNames(mainDirectoryPath);
    }

    @Override
    public LearnSet getSetByName(LearnSetName aSetName) throws IOException, ClassNotFoundException
    {
        LearnSetDto learnSetDto = fileReader.readLearnSet(getFile(aSetName));
        return learnSetDto.toLearnSet();
    }

    @Override
    public void saveChanges(LearnSet aSetToSave) throws IOException {
        File aFileToSave = getFile(aSetToSave.getLearnSetName());
        if (!aFileToSave.exists()) {
            saveAs(aSetToSave);
        }
        FileObjectWriter objectWriter = new FileObjectWriter();
        LearnSetDto dtoToSave = new LearnSetDto(aSetToSave);
        objectWriter.writeObjectToFile(dtoToSave, aFileToSave);
        aSetToSave.setSaved();
    }

    @Override
    public void saveAs(LearnSet aSetToSave) throws IOException {
        File aFileToSave = getFile(aSetToSave.getLearnSetName());
        if (aFileToSave.exists()) {
            throw new IOException("The set cannot be saved, a set with similar filename already exists");
        }
        LearnSetDto dtoToSave = new LearnSetDto(aSetToSave);
        fileWriter.writeObjectToFile(dtoToSave, aFileToSave);
        aSetToSave.setSaved();
    }


    private File getFile(LearnSetName aLearnSetName)
    {
        String fileName = aLearnSetName.toString() + "." + FILE_EXTENSION;
        Path pathToOriginFile = Path.of(mainDirectoryPath.toString(), fileName);
        return pathToOriginFile.toFile();
    }


}
