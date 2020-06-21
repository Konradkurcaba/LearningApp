package pl.kurcaba.learn.helper.learnset.view;

import pl.kurcaba.learn.helper.learnset.model.LearnCase;

public class LearnCaseViewDirector
{
    private final LearnCaseView.Builder builder;

    public LearnCaseViewDirector(LearnCaseView.Builder builder)
    {
        this.builder = builder;
    }

    public LearnCaseView buildFromDto(LearnCase aDto)
    {
        return builder
                .setDefinition(aDto.getDefinition())
                .setName(aDto.getName())
                .setImage(aDto.getImage())
                .build();
    }

}
