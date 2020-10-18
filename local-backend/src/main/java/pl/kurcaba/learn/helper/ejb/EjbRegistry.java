package pl.kurcaba.learn.helper.ejb;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class EjbRegistry
{
    private static final String EJB_NAMESPACE = "ejb";
    private static final String EAR_NAME_PROPERTY = "remote.module.ear.name";
    private static final String JAR_NAME_PROPERTY = "backend.module.jar.name";
    private static Properties EJB_PROPERTIES;

    static
    {
        EJB_PROPERTIES = new Properties();
        try
        {
            EJB_PROPERTIES.load(EjbRegistry.class.getClassLoader().getResourceAsStream("ejb.properties"));
        } catch (IOException e)
        {
            //won't happen - loading from resource directory
        }
    }

    private static final Map<Object, Object> ejbMap = new HashMap<>();

    public static <T> void registerEjb(Class<T> aRemoteInterface, String aEjbName) throws NamingException
    {
        final Context ctx = new InitialContext();
        Object ejbProxy = ctx.lookup(createJndiName(aRemoteInterface, aEjbName));
        if (aRemoteInterface.isInstance(ejbProxy))
        {
            ejbMap.put(aRemoteInterface, ejbProxy);
        } else
        {
            throw new IllegalArgumentException("The Given interface does not match to result");
        }
    }

    public static <T> Optional<T> getRegisteredEjb(Class<T> aRemoteInterface)
    {
        Object cachedResult = ejbMap.get(aRemoteInterface);
        if (cachedResult != null)
        {
            return Optional.of(aRemoteInterface.cast(cachedResult));
        } else
        {
            return Optional.empty();
        }
    }

    public static <T> String createJndiName(Class<T> aInterface, String aEjbName)
    {
        return EJB_NAMESPACE + ":" + EJB_PROPERTIES.getProperty(EAR_NAME_PROPERTY) + "/"
                + EJB_PROPERTIES.getProperty(JAR_NAME_PROPERTY) + "/" + aEjbName + "!" + aInterface.getCanonicalName();
    }


}
