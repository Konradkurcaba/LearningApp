package pl.kurcaba.learn.helper.learnset;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
    private boolean hasNext;
    private boolean hasPrev;

    public void setLearnCase(LearnCaseView learnCase)
    {
        this.learnCase = learnCase;
        prepareForDisplay();
    }

    private void prepareForDisplay()
    {
        nextButton.setVisible(hasNext);
        prevButton.setVisible(hasPrev);

        name.setText(learnCase.getName());
        definition.setText(learnCase.getDefinition());
        imageView.setImage(learnCase.getImage());
    }
}
