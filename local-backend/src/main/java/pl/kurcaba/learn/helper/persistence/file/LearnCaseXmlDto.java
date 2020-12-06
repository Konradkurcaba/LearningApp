package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.common.model.LearnCase;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name = "LearnCase")
class LearnCaseXmlDto
{

    @XmlElement
    private UUID id;
    @XmlElement
    private String name;
    @XmlElement
    private String definition;
    @XmlElement
    private boolean isUsedToLearn;

    @XmlElement(name = "images")
    private List<String> imageFilenames = new ArrayList<>();

    public LearnCaseXmlDto()
    {
    }

    public LearnCaseXmlDto(LearnCase aLearnCase)
    {
        this.id = aLearnCase.getId();
        this.name = aLearnCase.getName();
        this.definition = aLearnCase.getDefinition();
        this.isUsedToLearn = aLearnCase.isUsedToLearn();
    }

    @XmlTransient
    public void setImageFilenames(List<String> imageFilenames)
    {
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
