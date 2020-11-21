package pl.kurcaba.learn.helper.remote.backend;


import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.LearnSetNameFormatException;
import pl.kurcaba.learn.helper.remote.backend.interceptors.LogParameters;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class LearnSetValidator
{
    private Set<String> forbiddenSetNames;

    @PostConstruct
    private void initForbiddenSetNames()
    {
        forbiddenSetNames = new HashSet<>();
        forbiddenSetNames.add("forbidden1");
        forbiddenSetNames.add("forbidden2");
        forbiddenSetNames.add("forbidden3");
    }

    @LogParameters
    public boolean checkIfSetIsCorrect(LearnSet learnSet)
    {
        for (String forbiddenName : forbiddenSetNames)
        {
            try
            {
                if (learnSet.getLearnSetName().equals(new LearnSetName(forbiddenName)))
                {
                    return false;
                }
            } catch (LearnSetNameFormatException e)
            {
                return false;
            }
        }
        return true;
    }


}
