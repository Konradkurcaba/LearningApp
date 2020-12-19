package pl.kurcaba.learn.helper.persistence.file;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.Instant;

public class InstantXmlAdapter extends XmlAdapter<String, Instant>
{
    @Override
    public Instant unmarshal(String aString) throws Exception
    {
        return Instant.parse(aString);
    }

    @Override
    public String marshal(Instant instant) throws Exception
    {
        return instant.toString();
    }
}
