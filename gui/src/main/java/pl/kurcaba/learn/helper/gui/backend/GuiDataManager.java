package pl.kurcaba.learn.helper.gui.backend;

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
    public static final String DATA_DIRECTORY_NAME = "data";

    public LearnSetDaoIf initializeLocalDataManager()
    {
        Path currentPath = Paths.get("").toAbsolutePath();
        Path pathToDataDirectory = Path.of(currentPath.toString(), DATA_DIRECTORY_NAME);

        if(!pathToDataDirectory.toFile().exists())
        {
            pathToDataDirectory.toFile().mkdirs();
        }

        return new LearnSetFileDao(pathToDataDirectory);
    }

    public LearnSetDaoIf initializeRemoteDataManager() throws NamingException, IOException
    {
        EjbRegistry.registerEjb(LearnSetDaoIf.class, "RemoteDao");
        Optional<LearnSetDaoIf> dao = EjbRegistry.getRegisteredEjb(LearnSetDaoIf.class);
        if(dao.isPresent())
        {
            return dao.get();
        }else
        {
            throw new IllegalStateException("Remote dao is not registered");
        }
    }
}
