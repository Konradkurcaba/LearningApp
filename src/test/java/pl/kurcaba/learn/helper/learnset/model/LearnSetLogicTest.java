package pl.kurcaba.learn.helper.learnset.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kurcaba.learn.helper.learnset.view.LearnCaseView;

import java.util.ArrayList;
import java.util.List;

class LearnSetLogicTest
{

    private LearnSetLogic learnSetLogic;



    @BeforeEach
    private void prepareLearnSetModel()
    {
        LearnSetModel learnSetModel = new LearnSetModel();
        learnSetModel.getLearnSetCases().addAll(prepareListOfCases());
        learnSetLogic = new LearnSetLogic(learnSetModel);
    }

    private List<LearnCase> prepareListOfCases()
    {
        List<LearnCase> listOfCases = new ArrayList<>();
        LearnCase case1 = new LearnCase("desk","biurko", null);
        LearnCase case2 = new LearnCase("drawer","szuflada", null);
        LearnCase case3 = new LearnCase("commode","komoda", null);

        listOfCases.add(case1);
        listOfCases.add(case2);
        listOfCases.add(case3);

        return listOfCases;
    }

    private void compareCaseModelToCaseView(LearnCase aModel, LearnCaseView aView )
    {
        Assertions.assertEquals(aModel.getName(),aView.getName());
        Assertions.assertEquals(aModel.getDefinition(),aView.getDefinition());
        Assertions.assertEquals(aModel.getImage(),aView.getImage());
    }

    @Test
    void shouldReturnEmptyWhenOutOfIndex()
    {
        List<LearnCase> listToCompare = prepareListOfCases();

        for(int i = 0;i<3;i++)
        {
            LearnCaseView currentCase = learnSetLogic.getNextCaseView().get();
            compareCaseModelToCaseView(listToCompare.get(i),currentCase);
        }
        Assertions.assertTrue(learnSetLogic.getNextCaseView().isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenCounterSmallerThan1()
    {
        List<LearnCase> listToCompare = prepareListOfCases();

        //to iterate forward
        for(int i = 0;i<3;i++)
        {
            LearnCaseView currentCase = learnSetLogic.getNextCaseView().get();
            compareCaseModelToCaseView(listToCompare.get(i),currentCase);
        }

        for(int i = 2;i>0;i--)
        {
            LearnCaseView currentCase = learnSetLogic.getPrevCaseView().get();
            compareCaseModelToCaseView(listToCompare.get(i-1),currentCase);
        }
        Assertions.assertTrue(learnSetLogic.getPrevCaseView().isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenCounterIsNotSet()
    {
        Assertions.assertTrue(learnSetLogic.getCurrentCaseView().isEmpty());
    }

    @Test
    void shouldDeleteCorrectCaseModel()
    {
        List<LearnCase> listToCompare = prepareListOfCases();
        learnSetLogic.setAndGetFirst();
        for(int i = 1;i<3;i++)
        {
            learnSetLogic.deleteCurrentCase();
            compareCaseModelToCaseView(listToCompare.get(i),learnSetLogic.getCurrentCaseView().get());
        }
        learnSetLogic.deleteCurrentCase();
        Assertions.assertTrue(learnSetLogic.getCurrentCaseView().isEmpty());
    }

    @Test
    void shouldDeleteCorrectCaseModelFromEnd()
    {
        List<LearnCase> listToCompare = prepareListOfCases();
        //set index to the least element
        learnSetLogic.setAndGetFirst();
        learnSetLogic.getNextCaseView();
        learnSetLogic.getNextCaseView();

        for(int i = 2;i>0;i--)
        {
            learnSetLogic.deleteCurrentCase();
            compareCaseModelToCaseView(listToCompare.get(i-1),learnSetLogic.getCurrentCaseView().get());
        }
        learnSetLogic.deleteCurrentCase();
        Assertions.assertTrue(learnSetLogic.getCurrentCaseView().isEmpty());
    }

    @Test
    void shouldGenerateCorrectCaseView()
    {
        LearnCaseView caseView = learnSetLogic.setAndGetFirst().get();
        Assertions.assertTrue(caseView.hasNext());
        Assertions.assertFalse(caseView.hasPrev());
        caseView = learnSetLogic.getNextCaseView().get();
        Assertions.assertTrue(caseView.hasNext());
        Assertions.assertTrue(caseView.hasPrev());
        caseView = learnSetLogic.getNextCaseView().get();
        Assertions.assertFalse(caseView.hasNext());
        Assertions.assertTrue(caseView.hasPrev());
    }



}