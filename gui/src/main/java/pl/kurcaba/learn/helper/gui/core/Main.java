package pl.kurcaba.learn.helper.gui.core;

import pl.kurcaba.learn.helper.gui.backend.GuiDataManager;

import javax.naming.NamingException;
import java.io.IOException;

public class Main
{
    public static void main(String... args)
    {
        FXApplication.launch(FXApplication.class,null);
    }

//    public static void main(String... args) throws NamingException, IOException
//    {
//        GuiDataManager dataManager = new GuiDataManager();
//        dataManager.initializeRemoteDataManager();
//    }
}
