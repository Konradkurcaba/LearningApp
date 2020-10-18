package pl.kurcaba.learn.helper.gui.core;

import pl.kurcaba.learn.helper.common.model.LearnSetDaoIf;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.ejb.EjbRegistry;
import pl.kurcaba.learn.helper.persistence.file.FileObjectWriter;
import pl.kurcaba.learn.helper.persistence.file.LearnSetFileDao;
import pl.kurcaba.learn.helper.persistence.file.LearnSetReader;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;
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

        FileObjectWriter fileWriter = new FileObjectWriter();
        LearnSetReader fileReader = new LearnSetReader();
        return new LearnSetFileDao(pathToMainDirectory, fileReader, fileWriter);
    }

    public void initializeRemoteDataManager() throws NamingException, IOException
    {
        EjbRegistry.registerEjb(LearnSetDaoIf.class, "RemoteDao");
        Optional<LearnSetDaoIf> dao = EjbRegistry.getRegisteredEjb(LearnSetDaoIf.class);
        System.out.println(dao.get().getAllNames());
    }
}
