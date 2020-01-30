package pl.kurcaba.learn.helper.learnset.model;

import pl.kurcaba.learn.helper.learnset.view.LearnCaseView;

import java.util.Optional;

public class LearnSetLogic
{
    private final LearnSetModel learnSet;
    private int currentCaseIndex = -1;

    public LearnSetLogic(LearnSetModel learnSet)
    {
        this.learnSet = learnSet;
    }

    public Optional<LearnCaseView> getNextCaseView()
    {
        if (currentCaseIndex < learnSet.getLearnSetCases().size() -1)
        {
            LearnCaseModel currentCase = learnSet.getLearnSetCases().get(++currentCaseIndex);
            return Optional.of(buildCaseViewFromModel(currentCase));
        }
        return Optional.empty();
    }

    public Optional<LearnCaseView> getPrevCaseView()
    {
        if (!(currentCaseIndex < 1))
        {
            LearnCaseModel currentCase = learnSet.getLearnSetCases().get(--currentCaseIndex);
            return Optional.of(buildCaseViewFromModel(currentCase));
        }
        return Optional.empty();
    }

    public void updateModel(LearnCaseView learnCaseView)
    {
        LearnCaseModel currentCase = learnSet.getLearnSetCases().get(currentCaseIndex);
        currentCase.setDefinition(learnCaseView.getDefinition());
        currentCase.setName(learnCaseView.getName());
        currentCase.setImage(learnCaseView.getImage());
    }

    public void deleteCurrentCase()
    {
        if(currentCaseIndex > -1)
        {
            learnSet.getLearnSetCases().remove(currentCaseIndex);
            if (currentCaseIndex > learnSet.getLearnSetCases().size() - 1)
            {
                currentCaseIndex--;
            }
        }
    }

    public Optional<LearnCaseView> setAndGetFirst()
    {
        if(learnSet.getLearnSetCases().size() > 0)
        {
            currentCaseIndex =0;
        }
        return getCurrentCaseView();
    }

    public Optional<LearnCaseView> getCurrentCaseView()
    {
        if(currentCaseIndex > -1)
        {
            return Optional.of(buildCaseViewFromModel(learnSet.getLearnSetCases().get(currentCaseIndex)));
        }else
        {
            return Optional.empty();
        }
    }

    private LearnCaseView buildCaseViewFromModel(LearnCaseModel currentCase)
    {
        return LearnCaseView.Builder.builder()
                .setDefinition(currentCase.getDefinition())
                .setName(currentCase.getName())
                .setImage(currentCase.getImage())
                .setHasNext(currentCaseIndex < learnSet.getLearnSetCases().size() - 1)
                .setHasPrev(currentCaseIndex > 0)
                .build();
    }

}
