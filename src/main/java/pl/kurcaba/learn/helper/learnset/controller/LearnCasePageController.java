package pl.kurcaba.learn.helper.learnset.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import pl.kurcaba.learn.helper.learnset.model.LearnSetLogic;
import pl.kurcaba.learn.helper.learnset.view.LearnCaseView;

import java.util.Optional;

public class LearnCasePageController
{
    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;
    @FXML
    private Label counter;
    @FXML
    private TextField name;
    @FXML
    private TextField definition;
    @FXML
    private ImageView imageView;

    private LearnCaseView learnCase;

    private LearnSetLogic learnSetLogic;

    public LearnCasePageController(LearnSetLogic learnSetLogic)
    {
        this.learnSetLogic = learnSetLogic;
        Optional<LearnCaseView> learnCaseViewOpt = learnSetLogic.setAndGetFirst();
        if(learnCaseViewOpt.isPresent())
        {
            learnCase = learnCaseViewOpt.get();
        }
        else
        {
            //to do
        }
    }

    public void reloadView()
    {
        nextButton.setVisible(learnCase.hasNext());
        prevButton.setVisible(learnCase.hasPrev());

        name.setText(learnCase.getName());
        definition.setText(learnCase.getDefinition());
        if(learnCase.getImage().isPresent() )
        {
            imageView.setImage(learnCase.getImage().get());
        }
    }
    public void configureEvents()
    {
        nextButton.setOnAction(event ->{
            Optional<LearnCaseView> caseOpt = learnSetLogic.getNextCaseView();
            if(caseOpt.isPresent())
            {
                learnCase = caseOpt.get();
                reloadView();
            }
        });

        prevButton.setOnAction(event ->{
            Optional<LearnCaseView> caseOpt = learnSetLogic.getPrevCaseView();
            if(caseOpt.isPresent())
            {
                learnCase = caseOpt.get();
                reloadView();
            }
        });
    }
}
