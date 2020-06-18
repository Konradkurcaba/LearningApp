package pl.kurcaba.learn.helper.persistence.json;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LearnCase
{
    private String name;
    private String definition;
    private String pathToPhoto;
}
