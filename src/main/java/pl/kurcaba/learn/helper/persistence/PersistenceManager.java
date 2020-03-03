package pl.kurcaba.learn.helper.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersistenceManager
{
    private final Path pathToMainDirectory;

    public PersistenceManager(Path pathToMainDirectory)
    {
        this.pathToMainDirectory = pathToMainDirectory;
    }

    public List<String> getAllSetsNames() throws IOException
    {
        return Files.walk(pathToMainDirectory)
                .sorted(Comparator.reverseOrder())
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    public LearnSetPersistenceController getPersistenceController(String aSetName) throws IOException
    {
        Optional<Path> setDirectory = Files.walk(pathToMainDirectory)
                .sorted(Comparator.reverseOrder())
                .filter(path ->{ return path.getFileName().toString().equals(aSetName);})
                .findAny();

        if(setDirectory.isPresent())
        {
            return new LearnSetPersistenceController(setDirectory.get());
        }
        else throw new IOException("It is not possible to load indicated file");
    }

}
