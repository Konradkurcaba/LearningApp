package pl.kurcaba.learn.helper.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import pl.kurcaba.learn.helper.learnset.model.LearnSetManager;
import pl.kurcaba.learn.helper.learnset.view.LearnCaseView;
import pl.kurcaba.learn.helper.util.TextFieldColorUtil;

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

    private LearnSetManager learnSetManager;

    public LearnViewController(LearnSetManager learnSetManager)
    {

    }

    public void reloadView()
    {


    }
    public void configureEvents()
    {
        nextButton.setOnAction(event ->{

        });

        prevButton.setOnAction(event ->{

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
