package pl.kurcaba.learn.helper.persistence.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.learnset.values.LearnSetNameFormatException;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class is used only for compliance with old saved files. Java serialization shouldn't be used to persistent data.
 */
@Deprecated
public class LearnSetDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger(LearnSetDto.class);

    private LearnSetName learnSetName;
    private LinkedHashSet<LearnCaseDto> learnCases;

    public LearnSetDto(LearnSet learnSet) {
        try
        {
            this.learnSetName = new LearnSetName(learnSet.getLearnSetName().toString());
            learnCases = learnSet.getLearnSetCases().stream()
                    .map(LearnCaseDto::new).collect(Collectors.toCollection(LinkedHashSet::new));
        } catch (LearnSetNameFormatException aEx)
        {
            LOG.error("Cannot read given LearnSetName, there is a problem with LearnSetName", aEx);
        }
    }

    public LearnSet toLearnSet()
    {
        try
        {
            pl.kurcaba.learn.helper.common.values.LearnSetName setName
                    = new pl.kurcaba.learn.helper.common.values.LearnSetName(learnSetName.toString());
            return new LearnSet(setName, learnCases.stream()
                    .map(LearnCaseDto::toLearnCase)
                    .collect(Collectors.toCollection(LinkedHashSet::new)));
        } catch (pl.kurcaba.learn.helper.common.values.LearnSetNameFormatException aEx)
        {
            LOG.error("Cannot convert dto to LearnSet, there is a problem with LearnSetName", aEx);
        }
        return null;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearnSetDto that = (LearnSetDto) o;
        return Objects.equals(learnSetName, that.learnSetName) &&
                Objects.equals(learnCases, that.learnCases);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(learnSetName, learnCases);
    }
}
