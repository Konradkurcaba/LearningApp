package pl.kurcaba.learn.helper.persistence.file;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import pl.kurcaba.learn.helper.learnset.model.LearnCase;
import pl.kurcaba.learn.helper.learnset.model.LearnSet;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.learnset.values.LearnSetNameFormatException;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

class LearnSetFileDaoTest {

    private List<LearnSetName> createExampleSetNames() throws LearnSetNameFormatException {
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
    public void shouldDaoReturnsCorrectListOfLearnSetNames() throws IOException, LearnSetNameFormatException
    {
        //set up a test
        LearnSetReader reader = Mockito.mock(LearnSetReader.class);
        Mockito.when(reader.getAllNames(Paths.get("/testpath"))).thenReturn(createExampleSetNames());
        FileObjectWriter writer = new FileObjectWriter();
        LearnSetFileDao learnSetFileDao = new LearnSetFileDao(Paths.get("/testpath"), reader, writer);

        //a real test
        List<LearnSetName> returnedNames = learnSetFileDao.getAllNames();

        //assertions
        Assertions.assertEquals(createExampleSetNames(), returnedNames);
    }

    @Test
    public void shouldReturnCorrectLearnSetByName() throws IOException, ClassNotFoundException, LearnSetNameFormatException {

        //set up a test
        LearnSetReader reader = Mockito.mock(LearnSetReader.class);
        Mockito.when(reader.readLearnSet(Paths.get("/testpath/exampleName.lap").toFile()))
                .thenReturn(new LearnSetDto(createExampleLearnSet()));

        LearnSetFileDao learnSetFileDao = new LearnSetFileDao(Paths.get("/testpath"), reader, new FileObjectWriter());

        //a real test
        LearnSet returnedSet = learnSetFileDao.getSetByName(new LearnSetName("exampleName"));

        //assertions
        Assertions.assertEquals(createExampleLearnSet(), returnedSet);
    }

    @Test
    public void saveAsShouldCreateNewLearnSetFile(@TempDir Path tempDir) throws LearnSetNameFormatException
            , IOException, ClassNotFoundException {
        //set up a test
        LearnSetReader reader = new LearnSetReader();
        FileObjectWriter writer = new FileObjectWriter();
        LearnSetFileDao fileDao = new LearnSetFileDao(tempDir, reader, writer);

        //a real test
        fileDao.saveAs(createExampleLearnSet());
        LearnSet readSet = fileDao.getSetByName(createExampleLearnSet().getLearnSetName());

        //assertion
        Assertions.assertEquals(createExampleLearnSet(),readSet);
    }

    @Test
    public void saveAsShouldSaveChangesCorrectly(@TempDir Path tempDir) throws LearnSetNameFormatException
            , IOException, ClassNotFoundException {
        //set up a test
        LearnSetReader reader = new LearnSetReader();
        FileObjectWriter writer = new FileObjectWriter();
        LearnSetFileDao fileDao = new LearnSetFileDao(tempDir, reader, writer);
        LearnSet exampleLearnSet = createExampleLearnSet();
        fileDao.saveAs(exampleLearnSet);

        exampleLearnSet.addLearnCase(new LearnCase("exampleName","exampleDefinition"));

        //a real test
        fileDao.saveChanges(exampleLearnSet);

        //assertions
        LearnSet readSet = fileDao.getSetByName(exampleLearnSet.getLearnSetName());
        Assertions.assertEquals(exampleLearnSet, readSet);
    }

    @Test
    public void shouldRemoveCorrectFile(@TempDir Path aTempPath) throws LearnSetNameFormatException, IOException {
        //set up a test
        LearnSetReader reader = new LearnSetReader();
        FileObjectWriter writer = new FileObjectWriter();
        LearnSetFileDao fileDao = new LearnSetFileDao(aTempPath, reader, writer);
        Paths.get(aTempPath.toString(),"exampleFile.lap").toFile().createNewFile();

        //a real test
        fileDao.remove(new LearnSetName("exampleFile"));

        //assertion
        Assertions.assertEquals(0, aTempPath.toFile().list().length);
    }


}