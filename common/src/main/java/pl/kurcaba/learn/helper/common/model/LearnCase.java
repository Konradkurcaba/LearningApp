package pl.kurcaba.learn.helper.common.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class LearnCase implements Serializable
{
    private final UUID id;
    private String name;
    private String definition;
    private boolean isUsedToLearn;

    private byte[] image;

    public LearnCase(String name, String definition)
    {
        this.name = Objects.requireNonNullElse(name,"");
        this.definition = Objects.requireNonNullElse(definition,"");
        isUsedToLearn = true;
        id = UUID.randomUUID();
    }

    /**
     * This constructor should only be used when restoring from datasource.
     * @param name
     * @param definition
     * @param id
     */
    public LearnCase(String name, String definition, UUID id, boolean isUsedToLearn)
    {
        this.name = Objects.requireNonNullElse(name,"");
        this.definition = Objects.requireNonNullElse(definition,"");
        this.id = Objects.requireNonNull(id);
        this.isUsedToLearn = isUsedToLearn;
    }

    public UUID getId() {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDefinition()
    {
        return definition;
    }

    public void setDefinition(String definition)
    {
        this.definition = definition;
    }

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
    }

    public boolean isUsedToLearn()
    {
        return isUsedToLearn;
    }

    public void setUsedToLearn(boolean usedToLearn)
    {
        isUsedToLearn = usedToLearn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearnCase learnCase = (LearnCase) o;
        return id.equals(learnCase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
