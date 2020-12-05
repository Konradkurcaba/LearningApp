package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.common.values.LearnSetName;

import java.io.File;
import java.nio.file.Path;

public class AbstractLearnSetIO
{
    public static String LEGACY_FILE_EXTENSION = "lap";
    public static String V2_FILE_EXTENSION = "xdp";
    protected final Path pathToDataDirectory;

    public AbstractLearnSetIO(Path pathToDataDirectory)
    {
        this.pathToDataDirectory = pathToDataDirectory;
    }

    protected File getV2File(LearnSetName aLearnSetName)
    {
        String fileName = aLearnSetName.toString() + "." + V2_FILE_EXTENSION;
        return getFile(fileName);
    }

    protected File getLegacyFile(LearnSetName aLearnSetName)
    {
        String fileName = aLearnSetName.toString() + "." + LEGACY_FILE_EXTENSION;
        return getFile(fileName);
    }

    private File getFile(String filename)
    {
        Path pathToOriginFile = Path.of(pathToDataDirectory.toString(), filename);
        return pathToOriginFile.toFile();
    }



}
