package pl.kurcaba.learn.helper.common.model;


import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.NonUniqueException;

import javax.ejb.Remote;
import java.io.IOException;
import java.util.List;

@Remote
public interface LearnSetDaoIf
{
    List<LearnSetName> getAllNames() throws IOException;
    LearnSet getSetByName(LearnSetName aLearnSetName) throws IOException, ClassNotFoundException;
    void saveChanges(LearnSet aSetToSave) throws IOException;
    void saveAs(LearnSet aSetToSave) throws IOException;
    void remove(LearnSetName learnSet);
    LearnSet createNewLearnSet(LearnSetName aNewName) throws IOException, NonUniqueException;
}
