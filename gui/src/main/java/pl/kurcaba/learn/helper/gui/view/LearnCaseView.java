package pl.kurcaba.learn.helper.gui.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.WritableImage;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class LearnCaseView
{
    public static Builder builder(UUID aId)
    {
        return new Builder(aId);
    }
    private LearnCaseView(UUID aId) {
        id = aId;
    }

    private final UUID id;
    private String name;
    private String definition;
    private WritableImage image;
    private BooleanProperty isUsedToLearn;


    public String getName()
    {
        return name;
    }

    private void setName(String name)
    {
        this.name = name;
    }

    public String getDefinition()
    {
        return definition;
    }

    private void setDefinition(String definition)
    {
        this.definition = definition;
    }

    public Optional<WritableImage> getImage()
    {
        return Optional.ofNullable(image);
    }

    public void setImage(WritableImage image)
    {
        this.image = image;
    }

    public boolean isImageLoaded()
    {
        return image != null;
    }

    public UUID getId() {
        return id;
    }

    public BooleanProperty isUsedToLearnProperty()
    {
        return isUsedToLearn;
    }

    public boolean isUsedToLearn()
    {
        return isUsedToLearn.get();
    }

    public void setIsUsedToLearn(boolean isUsedToLearn)
    {
        this.isUsedToLearn = new SimpleBooleanProperty(isUsedToLearn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearnCaseView caseView = (LearnCaseView) o;
        return id.equals(caseView.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Builder
    {
        private UUID id;
        private String name;
        private String definition;
        private WritableImage image;
        private boolean isUsedToLearn;

        private Builder(UUID aId)
        {
            id = aId;
        }

        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }

        public Builder setDefinition(String definition)
        {
            this.definition = definition;
            return this;
        }

        public Builder setImage(WritableImage image)
        {
            this.image = image;
            return this;
        }

        public Builder setUsedToLearn(boolean usedToLearn)
        {
            isUsedToLearn = usedToLearn;
            return this;
        }

        public LearnCaseView build()
        {
            LearnCaseView learnCaseView = new LearnCaseView(id);
            learnCaseView.setName(Objects.requireNonNullElse(this.name, ""));
            learnCaseView.setDefinition(Objects.requireNonNullElse(this.definition, ""));
            learnCaseView.setImage(this.image);
            learnCaseView.setIsUsedToLearn(isUsedToLearn);
            return learnCaseView;
        }
    }
}
