package pl.kurcaba.learn.helper.persistence.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.LearnSetNameFormatException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashSet;

public class LearnSetWriterTest
{

    @Test
    public void saveAsShouldCreateNewLearnSetFileWithV2Extension(@TempDir Path tempDir)
            throws IOException, LearnSetNameFormatException
    {
        //set up a test
        LearnSetWriter writer = new LearnSetWriter(tempDir);

        //a real test
        writer.writeLearnSetToFile(new LearnSet(new LearnSetName("exampleName"), new LinkedHashSet<>()));

        //assertion
        Assertions.assertTrue(Path.of(tempDir.toString(), "exampleName.xdp").toFile().exists());
    }

}
