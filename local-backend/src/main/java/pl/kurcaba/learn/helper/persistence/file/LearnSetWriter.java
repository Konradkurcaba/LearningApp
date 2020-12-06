package pl.kurcaba.learn.helper.persistence.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.common.model.LearnCase;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.values.LearnSetName;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class LearnSetWriter extends AbstractLearnSetIO
{

    private static final Logger log = LogManager.getLogger(LearnSetWriter.class);

    public LearnSetWriter(Path aPathToDataDirectory)
    {
        super(aPathToDataDirectory);
    }

    public void writeLearnSetToFile(LearnSet aLearnSet) throws IOException
    {
        try(FileOutputStream fileOutputStream = new FileOutputStream(getV2File(aLearnSet.getLearnSetName()));
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);)
        {
            LearnSetXmlDto xmlDto = createXmlDto(aLearnSet,zipOutputStream);

            JAXBContext context = JAXBContext.newInstance(LearnSetXmlDto.class);
            Marshaller marshaller = context.createMarshaller();
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            marshaller.marshal(xmlDto, arrayOutputStream );

            ZipEntry entry = new ZipEntry(aLearnSet.getLearnSetName().toString());
            zipOutputStream.putNextEntry(entry);
            zipOutputStream.write(arrayOutputStream.toByteArray());
            zipOutputStream.closeEntry();

        } catch (JAXBException aEx)
        {
            throw new IOException("Cannot write learn set to file, there is a problem with JAXB", aEx);
        }
    }

    private LearnSetXmlDto createXmlDto(LearnSet aLearnSet, ZipOutputStream aZipFile) throws IOException
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
                try(aZipStream)
                {
                    String imageFilename = learnCase.getId().toString() + "-" + imageCounter++;
                    imageFilenames.add(imageFilename);
                    ZipEntry entry = new ZipEntry(imageFilename);
                    aZipStream.putNextEntry(entry);
                    aZipStream.write(image);
                    aZipStream.closeEntry();
                }
            }
            LearnCaseXmlDto learnCaseXmlDto = new LearnCaseXmlDto(learnCase);
            learnCaseXmlDto.setImageFilenames(imageFilenames);
        }
        return learnCases;
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
