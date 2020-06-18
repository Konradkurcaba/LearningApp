package pl.kurcaba.learn.helper.learnset.controller;

import javafx.scene.image.Image;
import pl.kurcaba.learn.helper.learnset.model.LearnCaseDto;
import pl.kurcaba.learn.helper.learnset.view.LearnCaseView;
import pl.kurcaba.learn.helper.learnset.view.LearnCaseViewDirector;

public class LearnCaseController
{

    private final LearnCaseDto learnCaseDto;

    private LearnCaseView learnCaseView;
    private final LearnCaseViewDirector viewDirector;

    public LearnCaseController(LearnCaseDto aLearnCaseDto, LearnCaseViewDirector aDirector)
    {
        learnCaseDto = aLearnCaseDto;
        viewDirector = aDirector;
        learnCaseView = viewDirector.buildFromDto(learnCaseDto);
    }

    public LearnCaseView getLearnCaseView()
    {
        return learnCaseView;
    }

    public void setName(String aNewName)
    {
        learnCaseDto.setName(aNewName);
    }
    public void setDefinition(String aNewDefinition)
    {
        learnCaseDto.setDefinition(aNewDefinition);
    }
    public void setImage(Image aNewImage)
    {
        learnCaseDto.setImage(aNewImage);
    }

    public LearnCaseView refreshView()
    {
        return viewDirector.buildFromDto(learnCaseDto);
    }



}
