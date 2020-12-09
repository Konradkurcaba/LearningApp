package pl.kurcaba.learn.helper.persistence.file;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "LearnSet")
class LearnSetXmlDto
{

    @XmlElement(name = "LearnSetName")
    private String learnSetName;
    @XmlElement(name = "LearnCases")
    private List<LearnCaseXmlDto> learnCases = new ArrayList<>();

    LearnSetXmlDto()
    {
    }

    public LearnSetXmlDto(String learnSetName, List<LearnCaseXmlDto> learnCases)
    {
        this.learnSetName = learnSetName;
        this.learnCases = learnCases;
    }

    @XmlTransient
    public void setLearnSetName(String learnSetName)
    {
        this.learnSetName = learnSetName;
    }

    @XmlTransient
    public void setLearnCases(List<LearnCaseXmlDto> learnCases)
    {
        this.learnCases = learnCases;
    }

    public List<LearnCaseXmlDto> getLearnCases()
    {
        return learnCases;
    }

    public String getLearnSetName()
    {
        return learnSetName;
    }
}
