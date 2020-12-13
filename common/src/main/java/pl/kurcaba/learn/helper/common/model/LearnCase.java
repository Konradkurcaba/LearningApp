package pl.kurcaba.learn.helper.common.model;

import javax.persistence.*;
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
    private Instant createDate = Instant.now();

    @OneToOne
    LearnSet parentLearnSet;

    @Lob
    @ElementCollection(fetch = FetchType.EAGER)
    private List<byte[]> images = new ArrayList<>();

    public LearnCase(String name, String definition)
    {
        this.name = Objects.requireNonNullElse(name, "");
        this.definition = Objects.requireNonNullElse(definition, "");
        isUsedToLearn = true;
        createDate = Instant.now();
    }

    public LearnCase()
    {
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

    public boolean isUsedToLearn()
    {
        return isUsedToLearn;
    }

    public void setUsedToLearn(boolean usedToLearn)
    {
        isUsedToLearn = usedToLearn;
    }

    public Instant getCreateDate()
    {
        return createDate;
    }

    @Override
    public int compareTo(LearnCase learnCase)
    {
        if(learnCase != null)
        {
            return this.createDate.compareTo(learnCase.createDate);
        }
        else
        {
            return 1;
        }
    }
}
