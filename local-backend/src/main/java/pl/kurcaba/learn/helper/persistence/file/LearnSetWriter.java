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
            ByteArrayOutputStream arrayOutputStream = marshalSetToInMemoryStream(xmlDto);
            writeBytesToZip(arrayOutputStream.toByteArray(), zipOutputStream, aLearnSet.getLearnSetName().toString());

            if(existsLegacy(aLearnSet.getLearnSetName()))
            {
                removeLegacyLearnSet(aLearnSet.getLearnSetName());
            }

        } catch (JAXBException aEx)
        {
            throw new IOException("Cannot write learn set to file, there is a problem with JAXB", aEx);
        }
    }

    private void writeBytesToZip(byte[] bytesToSave, ZipOutputStream zipStream, String aEntryName) throws IOException
    {
        ZipEntry entry = new ZipEntry(aEntryName);
        zipStream.putNextEntry(entry);
        zipStream.write(bytesToSave);
    }

    private ByteArrayOutputStream marshalSetToInMemoryStream(LearnSetXmlDto xmlDto) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(LearnSetXmlDto.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        marshaller.marshal(xmlDto, arrayOutputStream );
        return arrayOutputStream;
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
                writeBytesToZip(image,aZipStream, imageFilename);
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
}
