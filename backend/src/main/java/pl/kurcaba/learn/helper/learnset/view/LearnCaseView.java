package pl.kurcaba.learn.helper.learnset.view;

import java.util.Objects;
import java.util.Optional;

public class LearnCaseView
{
    public static Builder builder()
    {
        return new Builder();
    }
    private LearnCaseView() { }

    private String name;
    private String definition;
    private byte[] image;

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

    public Optional<byte[]> getImage()
    {
        return Optional.ofNullable(image);
    }

    private void setImage(byte[] image)
    {
        this.image = image;
    }

    public boolean isImageLoaded()
    {
        return image != null;
    }

    public static class Builder
    {
        private String name;
        private String definition;
        private byte[] image;

        private Builder()
        {
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

        public Builder setImage(byte[] image)
        {
            this.image = image;
            return this;
        }

        public LearnCaseView build()
        {
            LearnCaseView learnCaseView = new LearnCaseView();
            learnCaseView.setName(Objects.requireNonNullElse(this.name, ""));
            learnCaseView.setDefinition(Objects.requireNonNullElse(this.definition, ""));
            learnCaseView.setImage(this.image);
            return learnCaseView;
        }
    }
}
