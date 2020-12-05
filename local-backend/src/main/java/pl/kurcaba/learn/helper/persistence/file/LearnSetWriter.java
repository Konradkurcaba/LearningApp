package pl.kurcaba.learn.helper.persistence.file;

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


public class LearnSetWriter extends AbstractLearnSetIO
{

    public LearnSetWriter(Path aPathToDataDirectory)
    {
        super(aPathToDataDirectory);
    }

    public void writeLearnSetToFile(LearnSet objectToWrite) throws IOException
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(LearnSetXmlDto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(createXmlDto(objectToWrite), getV2File(objectToWrite.getLearnSetName()));
        } catch (JAXBException aEx)
        {
            throw new IOException("Cannot write learn set to file, there is a problem with JAXB", aEx);
        }
    }

    private LearnSetXmlDto createXmlDto(LearnSet aLearnSet)
    {
        List<LearnCaseXmlDto> allCases = createAllCases(aLearnSet);
        return new LearnSetXmlDto(aLearnSet.getLearnSetName().toString(), allCases);
    }

    private List<LearnCaseXmlDto> createAllCases(LearnSet aLearnSet)
    {
        List<LearnCaseXmlDto> learnCases = new ArrayList<>();
        for(LearnCase learnCase : aLearnSet.getLearnSetCases())
        {
            
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
