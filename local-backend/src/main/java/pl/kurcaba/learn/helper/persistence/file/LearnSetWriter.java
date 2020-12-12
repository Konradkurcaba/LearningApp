package pl.kurcaba.learn.helper.persistence.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.common.model.LearnCase;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.NonUniqueException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


class LearnSetWriter extends AbstractLearnSetIO
{

    private static final Logger log = LogManager.getLogger(LearnSetWriter.class);

    public LearnSetWriter(Path aPathToDataDirectory)
    {
        super(aPathToDataDirectory);
    }

    public void writeLearnSetToFile(LearnSet aLearnSet) throws IOException
    {
        try(FileOutputStream fileOutputStream = new FileOutputStream(getV2File(aLearnSet.getLearnSetName()));
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream))
        {
            LearnSetXmlDto xmlDto = writeImagesToZip(aLearnSet,zipOutputStream);

            JAXBContext context = JAXBContext.newInstance(LearnSetXmlDto.class);
            Marshaller marshaller = context.createMarshaller();
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            marshaller.marshal(xmlDto, arrayOutputStream );

            ZipEntry entry = new ZipEntry(aLearnSet.getLearnSetName().toString());
            zipOutputStream.putNextEntry(entry);
            zipOutputStream.write(arrayOutputStream.toByteArray());

            if(existsLegacy(aLearnSet.getLearnSetName()))
            {
                removeLegacyLearnSet(aLearnSet.getLearnSetName());
            }

        } catch (JAXBException aEx)
        {
            throw new IOException("Cannot write learn set to file, there is a problem with JAXB", aEx);
        }
    }

    public void writeNewSetToFile(LearnSet aLearnSet) throws NonUniqueException, IOException
    {
        if (existsV2(aLearnSet.getLearnSetName()))
        {
            throw new NonUniqueException("Learn set name must be unique");
        }
        writeLearnSetToFile(aLearnSet);
    }

    private LearnSetXmlDto writeImagesToZip(LearnSet aLearnSet, ZipOutputStream aZipFile) throws IOException
    {
        List<LearnCaseXmlDto> allCases = createAllCases(aLearnSet, aZipFile);
        return new LearnSetXmlDto(aLearnSet.getLearnSetName().toString(), allCases);
    }

    private List<LearnCaseXmlDto> createAllCases(LearnSet aLearnSet, ZipOutputStream aZipStream) throws IOException
    {
        List<LearnCaseXmlDto> learnCases = new ArrayList<>();
        for(LearnCase learnCase : aLearnSet.getLearnSetCases())
        {
            int imageCounter = 0;
            List<String> imageFilenames = new ArrayList<>();
            for(byte[] image : learnCase.getImages())
            {
                String imageFilename = learnCase.getUuid() + "-" + imageCounter++;
                imageFilenames.add(imageFilename);
                ZipEntry entry = new ZipEntry(imageFilename);
                aZipStream.putNextEntry(entry);
                aZipStream.write(image);
                aZipStream.closeEntry();
            }
            LearnCaseXmlDto learnCaseXmlDto = new LearnCaseXmlDto(learnCase);
            learnCaseXmlDto.setImageFilenames(imageFilenames);
            learnCases.add(learnCaseXmlDto);
        }
        return learnCases;
    }


    public void removeLearnSet(LearnSetName aSetToRemove) throws IOException
    {
        if(existsLegacy(aSetToRemove))
        {
            Files.delete(getLegacyFile(aSetToRemove).toPath());
        }
        if(existsV2(aSetToRemove))
        {
            Files.delete(getV2File(aSetToRemove).toPath());
        }
    }

    private void removeLegacyLearnSet(LearnSetName aSetToRemove)
    {
        getLegacyFile(aSetToRemove).delete();
    }

    private File getFile(LearnSetName aLearnSetName)
    {
        String fileName = aLearnSetName.toString() + "." + LEGACY_FILE_EXTENSION;
        Path pathToOriginFile = Path.of(pathToDataDirectory.toString(), fileName);
        return pathToOriginFile.toFile();
    }
}
