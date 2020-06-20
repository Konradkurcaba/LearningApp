package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.learnset.model.LearnSet;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LearnSetReader
{

    private final Path pathToMainDirectory;

    public LearnSetReader(Path aPathToMainDirectory)
    {
        this.pathToMainDirectory = aPathToMainDirectory;
    }

    public List<String> getAllNames() throws IOException
    {
        return Files.walk(pathToMainDirectory)
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
