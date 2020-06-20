package pl.kurcaba.learn.helper.persistence;

import pl.kurcaba.learn.helper.learnset.model.LearnSet;

import java.io.IOException;
import java.util.List;

public interface LearnSetDaoIf
{
    List<String> getAllNames() throws IOException;
    LearnSet getSetByName(String aLearnSetName) throws IOException, ClassNotFoundException;
    void saveChanges(LearnSet aSetToSave) throws IOException;
    void saveAs(LearnSet aSetToSave) throws IOException;
}
