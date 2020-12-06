package pl.kurcaba.learn.helper.persistence.file;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pl.kurcaba.learn.helper.common.model.LearnCase;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.LearnSetNameFormatException;
import pl.kurcaba.learn.helper.common.values.NonUniqueException;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

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
        return new LearnSet(new LearnSetName("exampleName"), new LinkedHashSet<>());
    }


    @Test
    public void savedFileShouldBeReadCorrectly(@TempDir Path tempDir) throws LearnSetNameFormatException
            , IOException
    {
        //set up a test
        LearnSetReader reader = new LearnSetReader(tempDir);
        LearnSetWriter writer = new LearnSetWriter(tempDir);
        LearnSetFileDao fileDao = new LearnSetFileDao(reader, writer);
        LearnSet learnSetToWrite = createExampleLearnSet();

        //a real test
        fileDao.saveAs(createExampleLearnSet());
        LearnSet readLearnSet = reader.readLearnSet(learnSetToWrite.getLearnSetName());

        //assertion
        Assertions.assertEquals(learnSetToWrite, readLearnSet);
    }

    @Test
    public void saveAsShouldCreateNewLearnSetFile(@TempDir Path tempDir) throws LearnSetNameFormatException
            , IOException, ClassNotFoundException
    {
        //set up a test
        LearnSetReader reader = new LearnSetReader(tempDir);
        LearnSetWriter writer = new LearnSetWriter(tempDir);
        LearnSetFileDao fileDao = new LearnSetFileDao(reader, writer);

        //a real test
        fileDao.saveAs(createExampleLearnSet());
        LearnSet readSet = fileDao.getSetByName(createExampleLearnSet().getLearnSetName());

        //assertion
        Assertions.assertEquals(createExampleLearnSet(), readSet);
    }

    @Test
    public void saveAsShouldSaveChangesCorrectly(@TempDir Path tempDir) throws LearnSetNameFormatException
            , IOException, ClassNotFoundException
    {
        //set up a test
        LearnSetReader reader = new LearnSetReader(tempDir);
        LearnSetWriter writer = new LearnSetWriter(tempDir);
        LearnSetFileDao fileDao = new LearnSetFileDao(reader, writer);
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
    public void secondSaveAsShouldThrownException(@TempDir Path tempDir) throws LearnSetNameFormatException
            , IOException, ClassNotFoundException
    {
        //set up a test
        LearnSetReader reader = new LearnSetReader(tempDir);
        LearnSetWriter writer = new LearnSetWriter(tempDir);
        LearnSetFileDao fileDao = new LearnSetFileDao(reader, writer);
        LearnSet exampleLearnSet = createExampleLearnSet();
        fileDao.saveAs(exampleLearnSet);

        exampleLearnSet.addLearnCase(new LearnCase("exampleName", "exampleDefinition"));

        //a real test
        Assertions.assertThrows(IOException.class, () -> fileDao.saveAs(exampleLearnSet));
    }

    @Test
    public void shouldRemoveLegacyFile(@TempDir Path aTempPath) throws LearnSetNameFormatException, IOException
    {
        //set up a test
        LearnSetReader reader = new LearnSetReader(aTempPath);
        LearnSetWriter writer = new LearnSetWriter(aTempPath);
        LearnSetFileDao fileDao = new LearnSetFileDao(reader, writer);
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
        LearnSetReader reader = new LearnSetReader(aTempPath);
        LearnSetWriter writer = new LearnSetWriter(aTempPath);
        LearnSetFileDao fileDao = new LearnSetFileDao(reader, writer);
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
        LearnSetReader reader = new LearnSetReader(aTempPath);
        LearnSetWriter writer = new LearnSetWriter(aTempPath);
        LearnSetFileDao fileDao = new LearnSetFileDao(reader, writer);
        Paths.get(aTempPath.toString(), "exampleFile.xdp").toFile().createNewFile();
        Paths.get(aTempPath.toString(), "exampleFile.lap").toFile().createNewFile();

        //a real test
        fileDao.remove(new LearnSetName("exampleFile"));

        //assertion
        Assertions.assertEquals(0, aTempPath.toFile().list().length);
    }

    @Test
    public void shouldThrowExceptionWhenLearnSetNameIsNotUnique(@TempDir Path aTempPath) throws IOException, LearnSetNameFormatException
    {
        //set up a test
        LearnSetReader reader = new LearnSetReader(aTempPath);
        LearnSetWriter writer = new LearnSetWriter(aTempPath);
        LearnSetFileDao fileDao = new LearnSetFileDao(reader, writer);
        fileDao.saveAs(createExampleLearnSet());

        //a real test
        Assertions.assertThrows(NonUniqueException.class,
                () -> fileDao.createNewLearnSet(new LearnSetName("exampleName")));
    }

    @Test
    public void shouldRemoveLegacyFileWhileSaving(@TempDir Path aTempPath) throws IOException, LearnSetNameFormatException, NonUniqueException
    {
        //set up a test
        LearnSetReader reader = new LearnSetReader(aTempPath);
        LearnSetWriter writer = new LearnSetWriter(aTempPath);
        LearnSetFileDao fileDao = new LearnSetFileDao(reader, writer);
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
        LearnSetReader reader = new LearnSetReader(aTempPath);
        LearnSetWriter writer = new LearnSetWriter(aTempPath);
        LearnSetFileDao fileDao = new LearnSetFileDao(reader, writer);
        Paths.get(aTempPath.toString(), "exampleName.lap").toFile().createNewFile();

        //a real test
        fileDao.createNewLearnSet(new LearnSetName("exampleName"));

        //Assert
        Assertions.assertTrue(Paths.get(aTempPath.toString(), "exampleName.xdp").toFile().exists());
    }


}