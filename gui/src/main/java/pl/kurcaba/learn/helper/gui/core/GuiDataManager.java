package pl.kurcaba.learn.helper.gui.core;

import pl.kurcaba.learn.helper.common.model.LearnSetDaoIf;
import pl.kurcaba.learn.helper.persistence.file.LearnSetFileDao;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GuiDataManager
{
    public static final String DATA_DIRECTORY_NAME = "data";

    public LearnSetDaoIf initializeDataManager()
    {
        Path currentPath = Paths.get("").toAbsolutePath();
        Path pathToDataDirectory = Path.of(currentPath.toString(), DATA_DIRECTORY_NAME);

        if(!pathToDataDirectory.toFile().exists())
        {
            pathToDataDirectory.toFile().mkdirs();
        }

        return new LearnSetFileDao(pathToDataDirectory);
    }
}
