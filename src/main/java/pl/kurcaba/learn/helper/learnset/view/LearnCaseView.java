package pl.kurcaba.learn.helper.learnset.view;

import javafx.scene.image.Image;

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
    private Image image;

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

    public Optional<Image> getImage()
    {
        return Optional.ofNullable(image);
    }

    private void setImage(Image image)
    {
        this.image = image;
    }

    public static class Builder
    {
        private String name;
        private String definition;
        private Image image;


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

        public Builder setImage(Image image)
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
