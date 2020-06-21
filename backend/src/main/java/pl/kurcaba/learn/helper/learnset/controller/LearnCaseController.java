package pl.kurcaba.learn.helper.learnset.controller;

import pl.kurcaba.learn.helper.learnset.model.LearnCase;
import pl.kurcaba.learn.helper.learnset.view.LearnCaseView;
import pl.kurcaba.learn.helper.learnset.view.LearnCaseViewDirector;

public class LearnCaseController
{

    private final LearnCase learnCase;

    private LearnCaseView learnCaseView;
    private final LearnCaseViewDirector viewDirector;

    public LearnCaseController(LearnCase aLearnCase, LearnCaseViewDirector aDirector)
    {
        learnCase = aLearnCase;
        viewDirector = aDirector;
        learnCaseView = viewDirector.buildFromDto(learnCase);
    }

    public LearnCaseView getLearnCaseView()
    {
        return learnCaseView;
    }

    public void setName(String aNewName)
    {
        learnCase.setName(aNewName);
    }
    public void setDefinition(String aNewDefinition)
    {
        learnCase.setDefinition(aNewDefinition);
    }
    public void setImage(byte[] aNewImage)
    {
        learnCase.setImage(aNewImage);
    }

    public LearnCaseView refreshView()
    {
        return viewDirector.buildFromDto(learnCase);
    }



}
