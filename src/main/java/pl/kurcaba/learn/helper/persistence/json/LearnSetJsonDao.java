package pl.kurcaba.learn.helper.persistence.json;

import pl.kurcaba.learn.helper.learnset.model.LearnSetDto;
import pl.kurcaba.learn.helper.persistence.LearnSetDaoIf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class LearnSetJsonDao implements LearnSetDaoIf
{
    private final Path pathToMainDirectory;

    public LearnSetJsonDao(Path pathToMainDirectory)
    {
        this.pathToMainDirectory = pathToMainDirectory;
    }

    public List<String> getAllNames() throws IOException
    {
        return Files.walk(pathToMainDirectory)
                .sorted(Comparator.reverseOrder())
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    public LearnSetDto getSetByName(String aSetName) throws IOException
    {
        Optional<Path> setDirectory = Files.walk(pathToMainDirectory)
                .sorted(Comparator.reverseOrder())
                .filter(path -> path.getFileName().toString().equals(aSetName))
                .findAny();

        if(setDirectory.isPresent())
        {
//            return new LearnSetDao(setDirectory.get());
        }
        else throw new IOException("It is not possible to load the indicated file");
        return null;
    }


    @Override
    public void save(LearnSetDto aSetToSave)
    {

    }
}
