package pl.kurcaba.learn.helper.persistence.file;


import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.LearnSetNameFormatException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class LearnSetReader
{
    public static String LEGACY_FILE_EXTENSION = "lap";
    public static String V2_FILE_EXTENSION = "xdp";
    private final Path pathToDataDirectory;

    public LearnSetReader(Path aPathDataDirectory)
    {
        pathToDataDirectory = aPathDataDirectory;
    }

    public List<LearnSetName> getAllNames() throws IOException
    {
        return Files.walk(pathToDataDirectory)
                .filter(path -> path.toString().endsWith("." + LEGACY_FILE_EXTENSION)
                        || path.toString().endsWith("." + V2_FILE_EXTENSION) )
                .sorted(Comparator.reverseOrder())
                .map(Path::getFileName)
                .map(Path::toString)
                .map(name -> name.replace("." + LEGACY_FILE_EXTENSION, ""))
                .map(name -> name.replace("." + V2_FILE_EXTENSION, ""))
                .map(this::createLearnSetName)
                .collect(Collectors.toList());
    }

    private LearnSetName createLearnSetName(String aName)
    {
        try
        {
            return new LearnSetName(aName);
        } catch (LearnSetNameFormatException formatException)
        {
            //we expect it is not going to happen, because names read from the disc should have a correct format.
            throw new RuntimeException(formatException);
        }
    }

    public LearnSet readLearnSet(LearnSetName aNameToLoad) throws IOException
    {
        try
        {
            if (getV2File(aNameToLoad).exists())
            {
                return loadV2File(aNameToLoad);
            }
            else if (getLegacyFile(aNameToLoad).exists())
            {
                return loadLegacyFile(aNameToLoad);
            }
        } catch (IOException | ClassNotFoundException aEx)
        {
            //do nothing, we will throw our exception bellow.
        }
        throw new IOException("Cannot load learn set for given name, file for given name does not exist");
    }

    private LearnSet loadV2File(LearnSetName aNameToLoad)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(LearnSetXmlDto.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            LearnSetXmlDto readSet = (LearnSetXmlDto) unmarshaller.unmarshal(getV2File(aNameToLoad));

        } catch (JAXBException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private LearnSet loadLegacyFile(LearnSetName aSetToLoad) throws IOException, ClassNotFoundException
    {
        try (ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(getLegacyFile(aSetToLoad))))
        {
            return ((LearnSetDto) objectStream.readObject()).toLearnSet();
        }
    }

    private File getV2File(LearnSetName aLearnSetName)
    {
        String fileName = aLearnSetName.toString() + "." + V2_FILE_EXTENSION;
        return getFile(fileName);
    }

    private File getLegacyFile(LearnSetName aLearnSetName)
    {
        String fileName = aLearnSetName.toString() + "." + LEGACY_FILE_EXTENSION;
        return getFile(fileName);
    }

    private File getFile(String filename)
    {
        Path pathToOriginFile = Path.of(pathToDataDirectory.toString(), filename);
        return pathToOriginFile.toFile();
    }

    public boolean exists(LearnSetName learnSetName)
    {
        return getLegacyFile(learnSetName).exists() || getV2File(learnSetName).exists();
    }
}
