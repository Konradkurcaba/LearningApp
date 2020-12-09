package pl.kurcaba.learn.helper.persistence.file;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.common.model.LearnCase;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.LearnSetNameFormatException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


class LearnSetReader extends AbstractLearnSetIO
{

    public LearnSetReader(Path aPathDataDirectory)
    {
        super(aPathDataDirectory);
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

    private LearnSet loadV2File(LearnSetName aNameToLoad) throws IOException
    {
        try(ZipFile zipFile = new ZipFile(getV2File(aNameToLoad)))
        {
            ZipEntry xmlEntry = zipFile.getEntry(aNameToLoad.toString());
            InputStream stream = zipFile.getInputStream(xmlEntry);

            JAXBContext context = JAXBContext.newInstance(LearnSetXmlDto.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            LearnSetXmlDto readSet = (LearnSetXmlDto) unmarshaller.unmarshal(stream);
            return createLearnSet(readSet,zipFile);

        } catch (JAXBException | LearnSetNameFormatException e)
        {
            throw new IOException("Cannot load learn set for given name, file for given name does not exist");
        }
    }

    private LearnSet createLearnSet(LearnSetXmlDto readSet, ZipFile zipFile) throws IOException, LearnSetNameFormatException
    {
        List<LearnCase> learnCases = new ArrayList<>();
        for(LearnCaseXmlDto learnXmlCase : readSet.getLearnCases())
        {
            LearnCase learnCase = new LearnCase(learnXmlCase.getName(),learnXmlCase.getDefinition()
                    ,learnXmlCase.getId(), learnXmlCase.isUsedToLearn());
            List<byte[]> images = new ArrayList<>();
            for(String imageFilename : learnXmlCase.getImageFilenames())
            {
                InputStream imageInputStream = zipFile.getInputStream(zipFile.getEntry(imageFilename));
                images.add(imageInputStream.readAllBytes());
            }
            learnCase.setImages(images);
            learnCases.add(learnCase);
        }
        return new LearnSet(new LearnSetName(readSet.getLearnSetName()),new LinkedHashSet<>(learnCases));
    }

    private LearnSet loadLegacyFile(LearnSetName aSetToLoad) throws IOException, ClassNotFoundException
    {
        try (ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(getLegacyFile(aSetToLoad))))
        {
            return ((LearnSetDto) objectStream.readObject()).toLearnSet();
        }
    }
}
