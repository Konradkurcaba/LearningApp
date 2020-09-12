package pl.kurcaba.learn.helper.persistence.file;

import pl.kurcaba.learn.helper.learnset.model.LearnCase;

import java.io.Serializable;
import java.util.UUID;

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
        this.image = aLearnCase.getImage();
        this.isUsedToLearn = aLearnCase.isUsedToLearn();
    }

    public LearnCase toLearnCase()
    {
        LearnCase restoredCase = new LearnCase(name, definition, id, isUsedToLearn);
        restoredCase.setImage(image);
        return restoredCase;
    }
}
