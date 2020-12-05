package pl.kurcaba.learn.helper.persistence.file;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name = "LearnCase")
class LearnCaseXmlDto
{

    private UUID id;
    private String name;
    private String definition;
    private boolean isUsedToLearn;

    @XmlElement(name = "images")
    private List<String> imageFilenames;

    public LearnCaseXmlDto(UUID id, String name, String definition, boolean isUsedToLearn, List<String> imageFilenames)
    {
        this.id = id;
        this.name = name;
        this.definition = definition;
        this.isUsedToLearn = isUsedToLearn;
        this.imageFilenames = imageFilenames;
    }

    public UUID getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getDefinition()
    {
        return definition;
    }

    public boolean isUsedToLearn()
    {
        return isUsedToLearn;
    }

    public List<String> getImageFilenames()
    {
        return imageFilenames;
    }

}
