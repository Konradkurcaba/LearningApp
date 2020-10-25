package pl.kurcaba.learn.helper.common.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class LearnCase extends BaseEntity implements Comparable<LearnCase>
{
    private String name;
    private String definition;
    private boolean isUsedToLearn;
    private final Instant createDate;
    @JoinColumn
    LearnSet parentLearnSet;

    private List<byte[]> images = new ArrayList<>();

    public LearnCase(String name, String definition)
    {
        this.name = Objects.requireNonNullElse(name, "");
        this.definition = Objects.requireNonNullElse(definition, "");
        isUsedToLearn = true;
        createDate = Instant.now();
    }

    /**
     * This constructor should only be used when restoring from datasource.
     *
     * @param name
     * @param definition
     * @param id
     */
    public LearnCase(String name, String definition, UUID id, boolean isUsedToLearn, Instant aCreateDate)
    {
        this.name = Objects.requireNonNullElse(name, "");
        this.definition = Objects.requireNonNullElse(definition, "");
        this.isUsedToLearn = isUsedToLearn;
        setUuid(id.toString());
        this.createDate = aCreateDate;
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

    public List<byte[]> getImages()
    {
        return images;
    }

    public void setImages(List<byte[]> aImages)
    {
        this.images = aImages;
    }

    public byte[] getImage()
    {
        if(images.size() > 0)
        {
            return images.get(0);
        }
        return null;
    }

    public void setImage(byte[] aImages)
    {
        images.add(0, aImages);
    }



    public boolean isUsedToLearn()
    {
        return isUsedToLearn;
    }

    public void setUsedToLearn(boolean usedToLearn)
    {
        isUsedToLearn = usedToLearn;
    }

    @Override
    public int compareTo(LearnCase learnCase)
    {
        return this.createDate.compareTo(learnCase.createDate);
    }
}
