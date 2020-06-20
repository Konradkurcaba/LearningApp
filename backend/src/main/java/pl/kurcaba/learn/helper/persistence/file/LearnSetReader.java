package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.learnset.model.LearnSet;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static pl.kurcaba.learn.helper.persistence.file.LearnSetFileDao.FILE_EXTENSION;

public class LearnSetReader
{

    public LearnSetReader()
    { }

    public List<String> getAllNames(Path aPathToMainDirectory) throws IOException
    {
        return Files.walk(aPathToMainDirectory)
                .filter(path -> path.toString().endsWith("." + FILE_EXTENSION))
                .sorted(Comparator.reverseOrder())
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    public LearnSet readLearnSet(File aFileToRead) throws IOException, ClassNotFoundException {

        try(ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(aFileToRead)))
        {
            return(LearnSet) objectStream.readObject();
        }
    }
}
