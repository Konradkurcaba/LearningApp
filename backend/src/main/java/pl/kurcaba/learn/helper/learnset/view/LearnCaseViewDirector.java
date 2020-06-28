package pl.kurcaba.learn.helper.learnset.view;

import pl.kurcaba.learn.helper.learnset.model.LearnCase;

public class LearnCaseViewDirector
{
    public LearnCaseViewDirector()
    {}

    public LearnCaseView buildFromDto(LearnCase aDto)
    {
        LearnCaseView.Builder builder = LearnCaseView.builder(aDto.getId());
        return builder
                .setDefinition(aDto.getDefinition())
                .setName(aDto.getName())
                .setImage(aDto.getImage())
                .build();
    }

}
