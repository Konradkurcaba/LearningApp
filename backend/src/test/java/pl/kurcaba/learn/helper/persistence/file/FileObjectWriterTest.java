package pl.kurcaba.learn.helper.persistence.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

class FileObjectWriterTest
{

    @Test
    public void objectShouldBeWrittenCorrectly(@TempDir Path aPath) throws IOException, ClassNotFoundException {
        //set up test
        String exampleString = "exampleStringToWrite";
        File fileToWrite = Paths.get(aPath.toString(),"exampleFile").toFile();

        //real test
        FileObjectWriter writer = new FileObjectWriter();
        writer.writeObjectToFile(exampleString, fileToWrite);

        //reading saved values
        try(ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(fileToWrite)))
        {
            String readString = (String) objectStream.readObject();
            Assertions.assertEquals(exampleString, readString);
        }
    }


}