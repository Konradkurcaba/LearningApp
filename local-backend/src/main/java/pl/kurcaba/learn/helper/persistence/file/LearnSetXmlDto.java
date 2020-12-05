package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.common.values.LearnSetName;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "LearnSet")
class LearnSetXmlDto
{

    private String learnSetName;
    @XmlElement(name = "LearnCases")
    private List<LearnCaseXmlDto> learnCases;

    LearnSetXmlDto()
    {
    }

    public LearnSetXmlDto(String learnSetName, List<LearnCaseXmlDto> learnCases)
    {
        this.learnSetName = learnSetName;
        this.learnCases = learnCases;
    }

    public void setLearnSetName(String learnSetName)
    {
        this.learnSetName = learnSetName;
    }

    public void setLearnCases(List<LearnCaseXmlDto> learnCases)
    {
        this.learnCases = learnCases;
    }
}
