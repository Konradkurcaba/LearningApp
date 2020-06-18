package pl.kurcaba.learn.helper.learnset.model;

import javafx.scene.image.Image;

import java.util.Objects;

public class LearnCaseDto
{
    private String name;
    private String definition;

    private Image image;

    public LearnCaseDto(String name, String definition)
    {
        this.name = Objects.requireNonNullElse(name,"");
        this.definition = Objects.requireNonNullElse(definition,"");
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

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearnCaseDto that = (LearnCaseDto) o;
        return Objects.equals(name, that.name)
                && Objects.equals(definition, that.definition)
                && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, definition, image);
    }
}
