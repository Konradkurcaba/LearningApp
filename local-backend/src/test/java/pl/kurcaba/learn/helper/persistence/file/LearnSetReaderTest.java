package pl.kurcaba.learn.helper.persistence.file;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pl.kurcaba.learn.helper.common.model.LearnCase;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.LearnSetNameFormatException;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

class LearnSetReaderTest {

    private List<LearnSetName> createExampleSetNames() throws LearnSetNameFormatException
    {
        List<LearnSetName> names = new ArrayList<>();
        names.add(new LearnSetName("firstName"));
        names.add(new LearnSetName("secondName"));
        return names;
    }

    private LearnCase createExampleLearnCase()
    {
        LearnCase learnCase = new LearnCase("name", "definition"
                , UUID.fromString("6681ce3f-607f-42ca-8f91-6294646dad92"),true);
        byte[] dummyImage = new byte[2];
        learnCase.setImage(dummyImage);
        return learnCase;
    }

    @Test
    public void shouldLearnSetReaderReadNamesCorrectly(@TempDir Path tempDir) throws IOException, LearnSetNameFormatException {
        //set up test
        Files.createFile(Paths.get(tempDir.toString(), "firstName.lap"));
        Files.createFile(Paths.get(tempDir.toString(), "secondName.lap"));
        Files.createFile(Paths.get(tempDir.toString(), "firstFile.brokenExtension"));

        //real test
        LearnSetReader setReader = new LearnSetReader();
        List<LearnSetName> readNames = setReader.getAllNames(tempDir);

        //assertion
        Assertions.assertTrue(createExampleSetNames().containsAll(readNames)
                && readNames.containsAll(createExampleSetNames()));
    }


    @Test
    public void shouldLoadDtoCorrectly(@TempDir Path tempDir) throws IOException, LearnSetNameFormatException, ClassNotFoundException
    {
        //set up test
        Path fileToWrite = Paths.get(tempDir.toString(), "exampleFilename.lap");

        LearnSet learnSet = new LearnSet(new LearnSetName("exampleName"), new LinkedHashSet<>());
        learnSet.addLearnCase(createExampleLearnCase());

        LearnSetDto dtoToWrite = new LearnSetDto(learnSet);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileToWrite.toString())))
        {
            objectOutputStream.writeObject(dtoToWrite);
        }
        LearnSetReader reader = new LearnSetReader();

        //real test
        LearnSetDto readDto = reader.readLearnSet(fileToWrite.toFile());

        //assertions
        Assertions.assertEquals(dtoToWrite, readDto);
    }

}