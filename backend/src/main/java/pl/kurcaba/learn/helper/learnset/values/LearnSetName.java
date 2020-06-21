package pl.kurcaba.learn.helper.learnset.values;

public class LearnSetName {

    private final String name;

    public LearnSetName(String name) {
        if(name == null || name.isEmpty())
        {
            throw new IllegalArgumentException("Learn Set Name cannot be null or empty");
        }
        boolean isNameCorrect = name.matches("[0-9a-zA-Z_!\\-]+");
        if(!isNameCorrect)
        {
            throw new IllegalArgumentException("Learn Set name contains wrong signs");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
