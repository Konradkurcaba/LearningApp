package pl.kurcaba.learn.helper.persistence.file;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement(name = "Learn case")
public class LearnCaseXmlDto
{

    private UUID id;
    private String name;
    private String definition;
    private boolean isUsedToLearn;

    private String imageFilename;

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
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

    public boolean isUsedToLearn()
    {
        return isUsedToLearn;
    }

    public void setUsedToLearn(boolean usedToLearn)
    {
        isUsedToLearn = usedToLearn;
    }

    public String getImageFilename()
    {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename)
    {
        this.imageFilename = imageFilename;
    }
}
