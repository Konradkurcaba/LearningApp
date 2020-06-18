package pl.kurcaba.learn.helper.persistence;

import pl.kurcaba.learn.helper.learnset.model.LearnSetDto;

import java.io.IOException;
import java.util.List;

public interface LearnSetDaoIf
{
    List<String> getAllNames() throws IOException;
    LearnSetDto getSetByName(String aLearnSetName) throws IOException;
    void save(LearnSetDto aSetToSave);
}
