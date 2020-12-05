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
        Files.createFile(Paths.get(tempDir.toString(), "secondName.xdp"));
        Files.createFile(Paths.get(tempDir.toString(), "firstFile.brokenExtension"));

        //real test
        LearnSetReader setReader = new LearnSetReader(tempDir);
        List<LearnSetName> readNames = setReader.getAllNames();

        //assertion
        Assertions.assertTrue(createExampleSetNames().containsAll(readNames)
                && readNames.containsAll(createExampleSetNames()));
    }


    @Test
    public void readLegacyLearnSetShouldHaveCorrectLearnSetName(@TempDir Path tempDir)
            throws IOException, LearnSetNameFormatException
    {
        //set up test
        Path fileToWrite = Paths.get(tempDir.toString(), "exampleFilename.lap");

        LearnSet learnSetToWrite = new LearnSet(new LearnSetName("exampleName"), new LinkedHashSet<>());
        learnSetToWrite.addLearnCase(createExampleLearnCase());

        LearnSetDto dtoToWrite = new LearnSetDto(learnSetToWrite);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileToWrite.toString())))
        {
            objectOutputStream.writeObject(dtoToWrite);
        }
        LearnSetReader reader = new LearnSetReader(tempDir);

        //real test
        LearnSet readSet = reader.readLearnSet(new LearnSetName("exampleFilename"));

        //assertions
        Assertions.assertEquals(learnSetToWrite, readSet);
    }

    @Test
    public void readLegacyLearnSetShouldHaveCorrectCaseList(@TempDir Path tempDir)
            throws IOException, LearnSetNameFormatException
    {
        //set up test
        Path fileToWrite = Paths.get(tempDir.toString(), "exampleFilename.lap");

        LearnSet learnSetToWrite = new LearnSet(new LearnSetName("exampleName"), new LinkedHashSet<>());
        learnSetToWrite.addLearnCase(createExampleLearnCase());

        LearnSetDto dtoToWrite = new LearnSetDto(learnSetToWrite);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileToWrite.toString())))
        {
            objectOutputStream.writeObject(dtoToWrite);
        }
        LearnSetReader reader = new LearnSetReader(tempDir);

        //real test
        LearnSet readSet = reader.readLearnSet(new LearnSetName("exampleFilename"));

        //assertions
        Assertions.assertEquals(learnSetToWrite.getLearnSetCases(), readSet.getLearnSetCases());
    }

    @Test
    public void learnSetReaderShouldThrowExceptionWhenFileForGivenLearnSetDoesNotExist(@TempDir Path tempDir) throws LearnSetNameFormatException, IOException
    {
        //set up
        LearnSetReader reader = new LearnSetReader(tempDir);

        //assert
        Assertions.assertThrows(IOException.class,() -> reader.readLearnSet(new LearnSetName("nonExisting")));
    }

    @Test
    public void shouldRecognizeLegacyFileInExistChecking(@TempDir Path tempDir) throws IOException, LearnSetNameFormatException
    {
        //set up test
        Path fileToWrite = Paths.get(tempDir.toString(), "exampleFilename.lap");
        fileToWrite.toFile().createNewFile();
        LearnSetReader reader = new LearnSetReader(tempDir);

        //real test
        boolean exists = reader.exists(new LearnSetName("exampleFilename"));

        //assertions
        Assertions.assertTrue(exists);
    }

    @Test
    public void shouldRecognizeV2FileInExistChecking(@TempDir Path tempDir) throws IOException, LearnSetNameFormatException
    {
        //set up test
        Path fileToWrite = Paths.get(tempDir.toString(), "exampleFilename.xdp");
        fileToWrite.toFile().createNewFile();
        LearnSetReader reader = new LearnSetReader(tempDir);

        //real test
        boolean exists = reader.exists(new LearnSetName("exampleFilename"));

        //assertions
        Assertions.assertTrue(exists);
    }

    @Test
    public void shouldRecognizeAnyFileDoesNotExist(@TempDir Path tempDir) throws IOException, LearnSetNameFormatException
    {
        //set up test
        LearnSetReader reader = new LearnSetReader(tempDir);

        //real test
        boolean exists = reader.exists(new LearnSetName("exampleFilename"));

        //assertions
        Assertions.assertFalse(exists);
    }


}