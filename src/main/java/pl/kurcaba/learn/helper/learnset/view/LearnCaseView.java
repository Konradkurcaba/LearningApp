package pl.kurcaba.learn.helper.learnset.view;

import javafx.scene.image.Image;

import java.util.Objects;

public class LearnCaseView
{

    private LearnCaseView() { }

    private String name;
    private String definition;
    private Image image;

    private boolean hasNext;
    private boolean hasPrev;

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

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public boolean hasNext()
    {
        return hasNext;
    }

    public void setHasNext(boolean hasNext)
    {
        this.hasNext = hasNext;
    }

    public boolean hasPrev()
    {
        return hasPrev;
    }

    public void setHasPrev(boolean hasPrev)
    {
        this.hasPrev = hasPrev;
    }

    public static class Builder
    {
        private String name;
        private String definition;
        private Image image;

        private boolean hasNext;
        private boolean hasPrev;

        private Builder()
        {
        }

        public static Builder builder()
        {
            return new Builder();
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

        public Builder setHasNext(boolean hasNext)
        {
            this.hasNext = hasNext;
            return this;
        }

        public Builder setHasPrev(boolean hasPrev)
        {
            this.hasPrev = hasPrev;
            return this;
        }

        public LearnCaseView build()
        {
            LearnCaseView learnCaseView = new LearnCaseView();
            learnCaseView.setName(Objects.requireNonNullElse(this.name, ""));
            learnCaseView.setDefinition(Objects.requireNonNullElse(this.definition, ""));
            learnCaseView.setHasNext(this.hasNext);
            learnCaseView.setHasPrev(this.hasPrev);
            learnCaseView.setImage(this.image);
            return learnCaseView;
        }
    }
}
