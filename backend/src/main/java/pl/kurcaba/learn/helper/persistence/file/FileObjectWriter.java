package pl.kurcaba.learn.helper.persistence.file;

import java.io.*;

public class FileObjectWriter {

    private final File aFileToWrite;

    public FileObjectWriter(File aFile)
    {
        aFileToWrite = aFile;
    }

    public void writeObjectToFile(Serializable objectToWrite) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(aFileToWrite)))
        {
            objectOutputStream.writeObject(objectToWrite);
        }
    }
}
