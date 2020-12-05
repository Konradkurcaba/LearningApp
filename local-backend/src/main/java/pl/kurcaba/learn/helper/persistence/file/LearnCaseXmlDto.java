package pl.kurcaba.learn.helper.persistence.file;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name = "LearnCase")
public class LearnCaseXmlDto
{

    private UUID id;
    private String name;
    private String definition;
    private boolean isUsedToLearn;

    @XmlElement(name = "images")
    private List<String> imageFilenames;

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

    public List<String> getImageFilenames()
    {
        return imageFilenames;
    }

    public void setImageFilenames(List<String> imageFilenames)
    {
        this.imageFilenames = imageFilenames;
    }
}
