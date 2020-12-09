package pl.kurcaba.learn.helper.persistence.file;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pl.kurcaba.learn.helper.common.model.LearnCase;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.LearnSetNameFormatException;
import pl.kurcaba.learn.helper.common.values.NonUniqueException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.*;

class LearnSetFileDaoTest
{
    private List<LearnSetName> createExampleSetNames() throws LearnSetNameFormatException
    {
        List<LearnSetName> names = new ArrayList<>();
        names.add(new LearnSetName("firstName"));
        names.add(new LearnSetName("secondName"));
        return names;
    }

    private LearnSet createExampleLearnSet() throws LearnSetNameFormatException
    {
        LearnSet exampleSet = new LearnSet(new LearnSetName("exampleName"), new LinkedHashSet<>());
        LearnCase learnCase = new LearnCase("example", "case");
        byte[] exampleImage = new byte[2];
        exampleImage[0] = 1;
        exampleImage[1] = 1;
        learnCase.setImage(exampleImage);
        exampleSet.addLearnCase(learnCase);
        return exampleSet;
    }

    private LearnCase createExampleLearnCase()
    {
        LearnCase learnCase = new LearnCase("name", "definition"
                , UUID.fromString("6681ce3f-607f-42ca-8f91-6294646dad92"),true);
        byte[] dummyImage = new byte[2];
        learnCase.setImages(Arrays.asList(dummyImage));
        return learnCase;
    }

    @Test
    public void savedFileShouldBeReadCorrectly(@TempDir Path tempDir) throws LearnSetNameFormatException, IOException, NonUniqueException
    {
        //set up a test
        LearnSetReader reader = new LearnSetReader(tempDir);
        LearnSetWriter writer = new LearnSetWriter(tempDir);
        LearnSetFileDao fileDao = new LearnSetFileDao(tempDir);
        LearnSet learnSetToWrite = createExampleLearnSet();


        //a real test
        fileDao.saveAs(learnSetToWrite);
        LearnSet readLearnSet = reader.readLearnSet(learnSetToWrite.getLearnSetName());

        //assertion
        Assertions.assertEquals(learnSetToWrite, readLearnSet);
    }


    @Test
    public void saveAsShouldSaveChangesCorrectly(@TempDir Path tempDir) throws LearnSetNameFormatException, IOException, ClassNotFoundException, NonUniqueException
    {
        //set up a test
        LearnSetFileDao fileDao = new LearnSetFileDao(tempDir);
        LearnSet exampleLearnSet = createExampleLearnSet();
        fileDao.saveAs(exampleLearnSet);

        exampleLearnSet.addLearnCase(new LearnCase("exampleName", "exampleDefinition"));

        //a real test
        fileDao.saveChanges(exampleLearnSet);

        //assertions
        LearnSet readSet = fileDao.getSetByName(exampleLearnSet.getLearnSetName());
        Assertions.assertEquals(exampleLearnSet, readSet);
    }

    @Test
    public void secondSaveAsShouldThrownException(@TempDir Path tempDir) throws LearnSetNameFormatException, IOException, ClassNotFoundException, NonUniqueException
    {
        //set up a test
        LearnSetFileDao fileDao = new LearnSetFileDao(tempDir);
        LearnSet exampleLearnSet = createExampleLearnSet();
        fileDao.saveAs(exampleLearnSet);

        exampleLearnSet.addLearnCase(new LearnCase("exampleName", "exampleDefinition"));

        //a real test
        Assertions.assertThrows( NonUniqueException.class, () -> fileDao.saveAs(exampleLearnSet));
    }

    @Test
    public void shouldRemoveLegacyFile(@TempDir Path aTempPath) throws LearnSetNameFormatException, IOException
    {
        //set up a test
        LearnSetFileDao fileDao = new LearnSetFileDao(aTempPath);
        Paths.get(aTempPath.toString(), "exampleFile.lap").toFile().createNewFile();

        //a real test
        fileDao.remove(new LearnSetName("exampleFile"));

        //assertion
        Assertions.assertEquals(0, aTempPath.toFile().list().length);
    }

    @Test
    public void shouldRemoveV2File(@TempDir Path aTempPath) throws LearnSetNameFormatException, IOException
    {
        //set up a test
        LearnSetFileDao fileDao = new LearnSetFileDao(aTempPath);
        Paths.get(aTempPath.toString(), "exampleFile.xdp").toFile().createNewFile();

        //a real test
        fileDao.remove(new LearnSetName("exampleFile"));

        //assertion
        Assertions.assertEquals(0, aTempPath.toFile().list().length);
    }

    @Test
    public void shouldRemoveV2FileAndLegacyFile(@TempDir Path aTempPath) throws LearnSetNameFormatException, IOException
    {
        //set up a test
        LearnSetFileDao fileDao = new LearnSetFileDao(aTempPath);
        Paths.get(aTempPath.toString(), "exampleFile.xdp").toFile().createNewFile();
        Paths.get(aTempPath.toString(), "exampleFile.lap").toFile().createNewFile();

        //a real test
        fileDao.remove(new LearnSetName("exampleFile"));

        //assertion
        Assertions.assertEquals(0, aTempPath.toFile().list().length);
    }

    @Test
    public void shouldThrowExceptionWhenLearnSetNameIsNotUnique(@TempDir Path aTempPath) throws IOException, LearnSetNameFormatException, NonUniqueException
    {
        //set up a test
        LearnSetFileDao fileDao = new LearnSetFileDao(aTempPath);
        fileDao.saveAs(createExampleLearnSet());

        //a real test
        Assertions.assertThrows(NonUniqueException.class,
                () -> fileDao.createNewLearnSet(new LearnSetName("exampleName")));
    }



    @Test
    public void shouldRemoveLegacyFileWhileSaving(@TempDir Path aTempPath) throws IOException, LearnSetNameFormatException, NonUniqueException
    {
        //set up a test
        LearnSetFileDao fileDao = new LearnSetFileDao(aTempPath);
        Paths.get(aTempPath.toString(), "exampleName.lap").toFile().createNewFile();

        //a real test
        fileDao.saveAs(new LearnSet(new LearnSetName("exampleName"), new LinkedHashSet<>()));

        //Assert
        Assertions.assertFalse(Paths.get(aTempPath.toString(), "exampleName.lap").toFile().exists());
    }

    @Test
    public void shouldCreateV2FileWhileSaving(@TempDir Path aTempPath) throws IOException, LearnSetNameFormatException, NonUniqueException
    {
        //set up a test
        LearnSetFileDao fileDao = new LearnSetFileDao(aTempPath);
        Paths.get(aTempPath.toString(), "exampleName.lap").toFile().createNewFile();

        //a real test
        fileDao.createNewLearnSet(new LearnSetName("exampleName"));

        //Assert
        Assertions.assertTrue(Paths.get(aTempPath.toString(), "exampleName.xdp").toFile().exists());
    }

    @Test
    public void readLegacyLearnSetShouldHaveCorrectLearnSetName(@TempDir Path tempDir) throws IOException
            , LearnSetNameFormatException, ClassNotFoundException
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
        learnSetToWrite.setSaved();
        LearnSetFileDao learnSetFileDao = new LearnSetFileDao(tempDir);

        //real test
        LearnSet readSet = learnSetFileDao.getSetByName(new LearnSetName("exampleFilename"));

        //assertions
        Assertions.assertEquals(learnSetToWrite, readSet);
    }

    @Test
    public void shouldLearnSetReaderReadNamesCorrectly(@TempDir Path tempDir) throws IOException, LearnSetNameFormatException {
        //set up test
        Files.createFile(Paths.get(tempDir.toString(), "firstName.lap"));
        Files.createFile(Paths.get(tempDir.toString(), "secondName.xdp"));
        Files.createFile(Paths.get(tempDir.toString(), "firstFile.brokenExtension"));

        //real test
        LearnSetFileDao fileDao = new LearnSetFileDao(tempDir);
        List<LearnSetName> readNames = fileDao.getAllNames();

        //assertion
        Assertions.assertTrue(createExampleSetNames().containsAll(readNames)
                && readNames.containsAll(createExampleSetNames()));
    }

    @Test
    public void readLegacyLearnSetShouldHaveCorrectCaseList(@TempDir Path tempDir) throws IOException, LearnSetNameFormatException, ClassNotFoundException
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
        learnSetToWrite.setSaved();
        LearnSetFileDao dao = new LearnSetFileDao(tempDir);

        //real test
        LearnSet readSet = dao.getSetByName(new LearnSetName("exampleFilename"));

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
    public void saveAsShouldCreateNewLearnSetFileWithV2Extension(@TempDir Path tempDir) throws IOException, LearnSetNameFormatException, NonUniqueException
    {
        //set up a test
        LearnSetFileDao dao = new LearnSetFileDao(tempDir);

        //a real test
        dao.saveAs(new LearnSet(new LearnSetName("exampleName"), new LinkedHashSet<>()));

        //assertion
        Assertions.assertTrue(Path.of(tempDir.toString(), "exampleName.xdp").toFile().exists());
    }


}