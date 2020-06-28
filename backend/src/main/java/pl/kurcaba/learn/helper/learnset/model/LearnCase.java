package pl.kurcaba.learn.helper.learnset.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class LearnCase implements Serializable
{
    private UUID id = UUID.randomUUID();
    private String name;
    private String definition;

    private byte[] image;

    public LearnCase(String name, String definition)
    {
        this.name = Objects.requireNonNullElse(name,"");
        this.definition = Objects.requireNonNullElse(definition,"");
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
