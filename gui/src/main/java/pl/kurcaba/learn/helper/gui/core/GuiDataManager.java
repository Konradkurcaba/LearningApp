package pl.kurcaba.learn.helper.gui.core;

import pl.kurcaba.learn.helper.common.model.LearnSetDaoIf;
import pl.kurcaba.learn.helper.ejb.EjbRegistry;
import pl.kurcaba.learn.helper.persistence.file.LearnSetFileDao;

import javax.naming.NamingException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class GuiDataManager
{
    public static final String MAIN_DIRECTORY_NAME = "data";

    public LearnSetDaoIf initializeLocalDataManager()
    {
        Path currentPath = Paths.get("").toAbsolutePath();
        Path pathToMainDirectory = Path.of(currentPath.toString(), MAIN_DIRECTORY_NAME);

        if (!pathToMainDirectory.toFile().exists())
        {
            pathToMainDirectory.toFile().mkdirs();
        }

        return new LearnSetFileDao(pathToMainDirectory);
    }

    public void initializeRemoteDataManager() throws NamingException, IOException
    {
        EjbRegistry.registerEjb(LearnSetDaoIf.class, "RemoteDao");
        Optional<LearnSetDaoIf> dao = EjbRegistry.getRegisteredEjb(LearnSetDaoIf.class);
        System.out.println(dao.get().getAllNames());
    }
}
