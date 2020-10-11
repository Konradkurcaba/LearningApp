package pl.kurcaba.learn.helper.persistence.file;


import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.LearnSetNameFormatException;

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

    public List<LearnSetName> getAllNames(Path aPathToMainDirectory) throws IOException
    {
        return Files.walk(aPathToMainDirectory)
                .filter(path -> path.toString().endsWith("." + FILE_EXTENSION))
                .sorted(Comparator.reverseOrder())
                .map(Path::getFileName)
                .map(Path::toString)
                .map(name -> name.replace("." + FILE_EXTENSION, ""))
                .map(this::createLearnSetName)
                .collect(Collectors.toList());
    }

    private LearnSetName createLearnSetName(String aName)
    {
        try
        {
            return new LearnSetName(aName);
        } catch (LearnSetNameFormatException formatException)
        {
            //we expect it is not going to happen, because names read from the disc should have a correct format.
            throw new RuntimeException(formatException);
        }
    }

    public LearnSetDto readLearnSet(File aFileToRead) throws IOException, ClassNotFoundException {

        try(ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(aFileToRead)))
        {
            return(LearnSetDto) objectStream.readObject();
        }
    }
}
