package pl.kurcaba.learn.helper.remote.backend;

public class DaoConstants
{
    public static final String ALL_NAMES_QUERY = "SELECT s.learnSetName FROM LearnSet s";
    public static final String LEARN_SET_BY_NAME = "SELECT l FROM LearnSet l WHERE l.learnSetName = ?1";
}
