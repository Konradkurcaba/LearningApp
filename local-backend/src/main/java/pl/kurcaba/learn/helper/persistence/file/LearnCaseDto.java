package pl.kurcaba.learn.helper.persistence.file;


import pl.kurcaba.learn.helper.common.model.LearnCase;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * This class is used only for compliance with old saved files. Java serialization shouldn't be used to persistent data.
 */
@Deprecated
public class LearnCaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private String definition;
    private boolean isUsedToLearn;

    private byte[] image;

    public LearnCaseDto(LearnCase aLearnCase) {
        this.id = aLearnCase.getId();
        this.name = aLearnCase.getName();
        this.definition = aLearnCase.getDefinition();
        if(aLearnCase.getImages().size() > 0)
        {
            this.image = aLearnCase.getImages().get(0);
        }
        this.isUsedToLearn = aLearnCase.isUsedToLearn();
    }

    public LearnCase toLearnCase()
    {
        LearnCase restoredCase = new LearnCase(name, definition, id, isUsedToLearn);
        restoredCase.setImages(Arrays.asList(image));
        return restoredCase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearnCaseDto that = (LearnCaseDto) o;
        return isUsedToLearn == that.isUsedToLearn &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(definition, that.definition) &&
                Arrays.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, definition, isUsedToLearn);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
