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
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


class LearnSetReader extends AbstractLearnSetIO
{

    private final Logger log = LogManager.getLogger(LearnSetReader.class);

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
        } catch (ClassNotFoundException aEx)
        {
            log.error("Problem with reading the legacy file:", aEx);
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
        TreeSet<LearnCase> learnCases = new TreeSet<>();
        for(LearnCaseXmlDto learnXmlCase : readSet.getLearnCases())
        {

            Instant createTime = getCreateTime(learnXmlCase);

            LearnCase learnCase = new LearnCase(learnXmlCase.getName(),learnXmlCase.getDefinition()
                    ,learnXmlCase.getId(), learnXmlCase.isUsedToLearn(), createTime);
            learnCases.add(learnCase);

            List<byte[]> images = readImages(zipFile, learnXmlCase);
            learnCase.setImages(images);
        }
        return new LearnSet(new LearnSetName(readSet.getLearnSetName()),learnCases);
    }

    /**
     * Gets create time from xml case. If create time is nullable, uses current time.
     * After assigning current time, method waits two ms. Thanks to it, each learn case has different create time.
     */
    private Instant getCreateTime(LearnCaseXmlDto learnXmlCase)
    {
        Instant createTime;
        if(learnXmlCase.getCreateTime() != null)
        {
            createTime = learnXmlCase.getCreateTime();
        }else
        {
            createTime = Instant.now();
            try
            {
                Thread.sleep(2);
            } catch (InterruptedException e)
            {
                //won't happen
            }
        }
        return createTime;
    }

    private List<byte[]> readImages(ZipFile zipFile, LearnCaseXmlDto learnXmlCase) throws IOException
    {
        List<byte[]> images = new ArrayList<>();
        for(String imageFilename : learnXmlCase.getImageFilenames())
        {
            InputStream imageInputStream = zipFile.getInputStream(zipFile.getEntry(imageFilename));
            images.add(imageInputStream.readAllBytes());
        }
        return images;
    }

    private LearnSet loadLegacyFile(LearnSetName aSetToLoad) throws IOException, ClassNotFoundException
    {
        try (ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(getLegacyFile(aSetToLoad))))
        {
            return ((LearnSetDto) objectStream.readObject()).toLearnSet();
        }
    }
}
