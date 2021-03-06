package pl.kurcaba.learn.helper.common.values;

import pl.kurcaba.learn.helper.common.model.BaseEntity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LearnSetName implements Serializable
{

    private String name;

    public LearnSetName(String name) throws LearnSetNameFormatException
    {
        if (name == null || name.isEmpty())
        {
            throw new LearnSetNameFormatException("Learn Set Name cannot be null or empty");
        }
        boolean isNameCorrect = name.matches("[0-9a-zA-Z_!\\-]+");
        if (!isNameCorrect)
        {
            throw new LearnSetNameFormatException("Learn Set name contains wrong signs");
        }
        this.name = name;
    }

    public LearnSetName()
    {
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearnSetName that = (LearnSetName) o;
        return Objects.equals(name.toUpperCase(), that.name.toUpperCase());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name.toUpperCase());
    }

    @Override
    public String toString()
    {
        return name;
    }
}
