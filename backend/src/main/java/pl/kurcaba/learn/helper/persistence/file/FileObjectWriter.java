package pl.kurcaba.learn.helper.persistence.file;

import java.io.*;

public class FileObjectWriter {

    public FileObjectWriter()
    {}

    public void writeObjectToFile(Serializable objectToWrite, File aFile) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(aFile)))
        {
            objectOutputStream.writeObject(objectToWrite);
        }
    }
}
