package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.learnset.values.LearnSetName;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class LearnSetXmlDto
{

    LearnSetXmlDto()
    {
    }

    public LearnSetXmlDto(String learnSetName, List<LearnCaseXmlDto> learnCases)
    {
        this.learnSetName = learnSetName;
        this.learnCases = learnCases;
    }

    private String learnSetName;

    @XmlElement(name = "learnCases")
    private List<LearnCaseXmlDto> learnCases;

    public String getLearnSetName()
    {
        return learnSetName;
    }

    public void setLearnSetName(String learnSetName)
    {
        this.learnSetName = learnSetName;
    }

    public List<LearnCaseXmlDto> getLearnCases()
    {
        return learnCases;
    }

    public void setLearnCases(List<LearnCaseXmlDto> learnCases)
    {
        this.learnCases = learnCases;
    }
}
