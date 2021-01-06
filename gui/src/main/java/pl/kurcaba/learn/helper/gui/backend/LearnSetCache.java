package pl.kurcaba.learn.helper.gui.backend;

import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.values.LearnSetName;

import java.util.HashMap;
import java.util.Optional;

/**
 * Caches LearnSets, always returns a deep clone of the given set.
 */
public class LearnSetCache
{
    private final HashMap<LearnSetName, LearnSet> map = new HashMap<>();

    /**
     * Puts a new learn set to cache, replaces an old set if cache had contained it before.
     */
    public void put(LearnSet aLearnSet)
    {
        map.put(aLearnSet.getLearnSetName(),new LearnSet(aLearnSet));
    }

    /**
     * @param aName name of requested learn set.
     * @return a learn set if cache contains learn set with given name, otherwise empty optional is returned.
     */
    public Optional<LearnSet> get(LearnSetName aName)
    {
        LearnSet cachedSet = map.get(aName);
        if(cachedSet == null)
        {
            return Optional.empty();
        }
        return Optional.of(new LearnSet(cachedSet));
    }

    public boolean isCached(LearnSetName aName)
    {
        return map.containsKey(aName);
    }


}
