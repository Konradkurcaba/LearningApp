package pl.kurcaba.learn.helper.learnset.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import pl.kurcaba.learn.helper.learnset.model.LearnSetLogic;
import pl.kurcaba.learn.helper.learnset.view.LearnCaseView;
import pl.kurcaba.learn.helper.util.TextFieldColorUtil;

import java.util.Optional;

public class LearnViewController
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
    @FXML
    private ToggleButton showName;
    @FXML
    private ToggleButton showDefinition;
    @FXML
    private Button checkAnswers;

    private LearnCaseView learnCase;

    private LearnSetLogic learnSetLogic;

    public LearnViewController(LearnSetLogic learnSetLogic)
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
        clearView();

        nextButton.setVisible(learnCase.hasNext());
        prevButton.setVisible(learnCase.hasPrev());

        if(showName.isSelected())
        {
            name.setText(learnCase.getName());
            name.setEditable(false);
        } else
        {
            name.setEditable(true);
        }
        if(showDefinition.isSelected())
        {
            definition.setText(learnCase.getDefinition());
            definition.setEditable(false);
        }
        else
        {
            definition.setEditable(true);
        }

        if(learnCase.getImage().isPresent() )
        {
            imageView.setImage(learnCase.getImage().get());
        }

        int totalCount = learnSetLogic.getNumberOfCases();
        int currentNumber = learnSetLogic.getCurrentCaseNumber();

        counter.setText(currentNumber + "/" + totalCount);

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

        showDefinition.setOnAction(event -> reloadView());
        showName.setOnAction(event -> reloadView());
        checkAnswers.setOnAction(event -> checkAnswers());
    }

    private void clearView()
    {
        TextFieldColorUtil.colorText(definition, TextFieldColorUtil.Color.BLACK);
        TextFieldColorUtil.colorText(name, TextFieldColorUtil.Color.BLACK);

        definition.clear();
        name.clear();
        imageView.setImage(null);
    }

    private void checkAnswers()
    {
        if(name.isEditable())
        {
            if(name.getText().equals(learnCase.getName()))
            {
                TextFieldColorUtil.colorText(name, TextFieldColorUtil.Color.GREEN);
            }else
            {
                TextFieldColorUtil.colorText(name, TextFieldColorUtil.Color.RED);
            }
        }
        if(definition.isEditable())
        {
            if(definition.getText().equals(learnCase.getDefinition()))
            {
                TextFieldColorUtil.colorText(definition, TextFieldColorUtil.Color.GREEN);
            }else
            {
                TextFieldColorUtil.colorText(definition, TextFieldColorUtil.Color.RED);
            }
        }
    }

}
