package pl.kurcaba.learn.helper.learnset;

import javafx.scene.image.Image;

public class LearnCaseModel
{
    private String name;
    private String definition;

    private Image image;


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
}
