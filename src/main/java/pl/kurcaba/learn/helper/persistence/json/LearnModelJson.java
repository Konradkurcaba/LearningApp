package pl.kurcaba.learn.helper.persistence.json;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class LearnModelJson
{

    private String setName;
    private List<LearnCaseJson> learnCases;

}
