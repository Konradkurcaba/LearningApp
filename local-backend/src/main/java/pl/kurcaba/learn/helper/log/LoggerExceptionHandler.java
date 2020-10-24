package pl.kurcaba.learn.helper.log;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerExceptionHandler implements Thread.UncaughtExceptionHandler
{
    private static final Logger logger = LogManager.getLogger(LoggerExceptionHandler.class);

    @Override
    public void uncaughtException(Thread aThread, Throwable aException)
    {
        logger.error("An exception has occurred", aException);
    }
}
