package pl.kurcaba.learn.helper.gui.dialogs.learn;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowController;
import pl.kurcaba.learn.helper.gui.controller.main.CommandIf;
import pl.kurcaba.learn.helper.gui.controlls.CommandButton;
import pl.kurcaba.learn.helper.gui.dialogs.options.LearnOptions;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.util.List;
import java.util.Optional;


public class LearnPanelController extends AbstractWindowController
{
    public static final PseudoClass CORRECT_CSS_CLASS = PseudoClass.getPseudoClass("correct");
    public static final PseudoClass INCORRECT_CSS_CLASS = PseudoClass.getPseudoClass("incorrect");

    @FXML
    private CommandButton checkButton;

    @FXML
    private TextField nameTf;
    @FXML
    private Label correctName;

    @FXML
    private TextField definitionTf;
    @FXML
    private Label correctDefinition;

    @FXML
    private CommandButton showAnswer;

    @FXML
    private ImageView imageView;
    @FXML
    private StackPane imageStackPane;

    @FXML
    private CommandButton prevButton;

    @FXML
    private CommandButton nextButton;

    @FXML
    private CommandButton nextImage;

    @FXML
    private CommandButton prevImage;


    private final LearnOptions learnOptions;

    final List<LearnCaseView> learnCases;
    private int currentCaseIndex;
    private int currentImageIndex;

    public LearnPanelController(LearnOptions learnOptions, List<LearnCaseView> learnCases)
    {
        this.learnOptions = learnOptions;
        this.learnCases = learnCases;
    }

    @Override
    public void initialize() {
        super.initialize();
        initButtons();
        initTextField();
        initLabels();
        displayFirstCase();
    }

    private void initLabels()
    {
        correctName.setVisible(false);
        correctDefinition.setVisible(false);
    }


    private void initTextField()
    {
        CommandIf clearCmd = new ClearTfPseudoClasses(this);
        nameTf.textProperty().addListener(changed -> clearCmd.executeCommand());
        definitionTf.textProperty().addListener( changed -> clearCmd.executeCommand() );
    }

    private void displayFirstCase() {
        displayCase(learnCases.get(0));
        currentCaseIndex = 0;
    }

    private void initButtons()
    {
        nextButton.setCommand(new NextCaseCmd(this));
        prevButton.setCommand(new PrevCaseCmd(this));
        prevButton.updateState();
        nextButton.updateState();
        checkButton.setCommand(new CheckCommand(this));
        showAnswer.setCommand(new ShowCorrectAnswersCmd(this));

        PrevImageCmd prevImageCmd = new PrevImageCmd(this);
        prevImage.setCommand(prevImageCmd);
        prevImage.disableProperty().bind(prevImageCmd.canBePerformedProperty().not());

        NextImageCmd nextImageCmd = new NextImageCmd(this);
        nextImage.setCommand(nextImageCmd);
        nextImage.disableProperty().bind(nextImageCmd.canBePerformedProperty().not());

    }

    private void initImageView()
    {
        Bounds imageViewBounds = searchMaxImageSize();
        imageStackPane.setMinHeight((imageViewBounds.getHeight()));
        imageStackPane.setMinWidth(imageViewBounds.getWidth());
        imageView.setFitWidth(0);
        imageView.setFitHeight(0);
    }

    void updateState()
    {
        nextButton.updateState();
        prevButton.updateState();
    }

    private Bounds searchMaxImageSize() {
        double maxHeight = 0d;
        double maxWidth = 0d;

        for(LearnCaseView learnCase : learnCases)
        {
            for(WritableImage image : learnCase.getImages())
            {
                if(image.getHeight() > maxHeight)
                {
                    maxHeight = image.getHeight();
                }
                if(image.getWidth() > maxWidth)
                {
                    maxWidth = image.getWidth();
                }
            }
        }
        return new BoundingBox(0, 0, maxWidth + 100, maxHeight);
    }

    int getCurrentCaseIndex() {
        return currentCaseIndex;
    }

    void setCurrentCaseIndex(int currentCaseIndex) {
        this.currentCaseIndex = currentCaseIndex;
    }

    void displayNextImage()
    {
        showImage(currentImageIndex++);
    }

    void displayPrevImage()
    {
        showImage(currentImageIndex++);
    }

    boolean canShowNextImage()
    {
        return currentImageIndex + 1 < getCurrentCaseView().getImages().size();
    }

    boolean canShowPrevImage()
    {
        return currentImageIndex > 0;
    }

    void displayCase(LearnCaseView aCaseView)
    {
        nameTf.clear();
        if(learnOptions.isNameShown())
        {
            nameTf.setText(aCaseView.getName());
            nameTf.setEditable(false);
        }
        definitionTf.clear();
        if(learnOptions.isDefinitionShown())
        {
            definitionTf.setText(aCaseView.getDefinition());
            definitionTf.setEditable(false);
        }

        imageView.setImage(null);
        if(learnOptions.isImageShown())
        {
            if(!aCaseView.getImages().isEmpty())
            {
                imageView.setImage(aCaseView.getImages().get(0));
            }
        }
        correctName.setVisible(false);
        correctDefinition.setVisible(false);
    }

    @Override
    public void setStage(Stage aStage) {
        super.setStage(aStage);
        initImageView();
    }

    LearnOptions getLearnOptions()
    {
        return learnOptions;
    }

    String getNameTextFieldValue() {
        return nameTf.getText();
    }

    String getDefinitionTextFieldValue() {
        return definitionTf.getText();
    }

    LearnCaseView getCurrentCaseView()
    {
        return learnCases.get(currentCaseIndex);
    }

    void addPseudoClassToNameTf(PseudoClass aPseudoClass)
    {
        nameTf.pseudoClassStateChanged(aPseudoClass, true);
    }

    void removePseudoClassFromNameTf(PseudoClass aPseudoClass)
    {
        nameTf.pseudoClassStateChanged(aPseudoClass, false);
    }

    void addPseudoClassToDefinitionTf(PseudoClass aPseudoClass)
    {
        definitionTf.pseudoClassStateChanged(aPseudoClass, true);
    }

    void removePseudoClassFromDefinitionTf(PseudoClass aPseudoClass)
    {
        definitionTf.pseudoClassStateChanged(aPseudoClass, false);
    }

    void showCorrectName()
    {
        correctName.setText(getCurrentCaseView().getName());
        correctName.setVisible(true);
    }

    void showCorrectDefinition()
    {
        correctDefinition.setText(getCurrentCaseView().getDefinition());
        correctDefinition.setVisible(true);
    }

    void showImage(int aIndex)
    {
        imageView.setImage(getCurrentCaseView().getImages().get(aIndex));
    }
}
