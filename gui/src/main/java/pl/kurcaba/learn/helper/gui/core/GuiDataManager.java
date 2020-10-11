package pl.kurcaba.learn.helper.gui.core;

import pl.kurcaba.learn.helper.common.model.LearnSetDaoIf;
import pl.kurcaba.learn.helper.persistence.file.FileObjectWriter;
import pl.kurcaba.learn.helper.persistence.file.LearnSetFileDao;
import pl.kurcaba.learn.helper.persistence.file.LearnSetReader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GuiDataManager
{
    public static final String MAIN_DIRECTORY_NAME = "data";

    public LearnSetDaoIf initializeDataManager()
    {
        Path currentPath = Paths.get("").toAbsolutePath();
        Path pathToMainDirectory = Path.of(currentPath.toString(), MAIN_DIRECTORY_NAME);

        if(!pathToMainDirectory.toFile().exists())
        {
            pathToMainDirectory.toFile().mkdirs();
        }

        FileObjectWriter fileWriter = new FileObjectWriter();
        LearnSetReader fileReader = new LearnSetReader();
        return new LearnSetFileDao(pathToMainDirectory,fileReader,fileWriter);
    }
}
