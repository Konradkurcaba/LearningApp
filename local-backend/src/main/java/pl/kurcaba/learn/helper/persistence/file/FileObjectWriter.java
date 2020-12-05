package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.common.values.LearnSetName;

import java.io.*;
import java.nio.file.Path;


public class FileObjectWriter
{

    public static String LEGACY_FILE_EXTENSION = "lap";
    public static String V2_FILE_EXTENSION = "xdp";
    private final Path pathToDataDirectory;


    public FileObjectWriter(Path aPathToDataDirectory)
    {
        pathToDataDirectory = aPathToDataDirectory;
    }

    public void writeObjectToFile(Serializable objectToWrite, LearnSetName aLearnSetName) throws IOException
    {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(getFile(aLearnSetName))))
        {
            objectOutputStream.writeObject(objectToWrite);
        }
    }

    public void removeLearnSet(LearnSetName aSetToRemove)
    {
        getFile(aSetToRemove).delete();
    }

    private File getFile(LearnSetName aLearnSetName)
    {
        String fileName = aLearnSetName.toString() + "." + LEGACY_FILE_EXTENSION;
        Path pathToOriginFile = Path.of(pathToDataDirectory.toString(), fileName);
        return pathToOriginFile.toFile();
    }
}
