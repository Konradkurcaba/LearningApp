package pl.kurcaba.learn.helper.gui.dialogs.learn;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
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
    private TextField definitionTf;

    @FXML
    private ImageView imageView;
    @FXML
    private StackPane imageStackPane;

    @FXML
    private CommandButton prevButton;

    @FXML
    private CommandButton nextButton;


    private final LearnOptions learnOptions;

    final List<LearnCaseView> learnCases;
    private int currentCaseIndex;

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
        displayFirstCase();
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
    }

    private void initImageView() {
        if(learnOptions.isImageShown())
        {
            Bounds imageViewBounds = searchMaxImageSize();
            imageStackPane.setMinHeight((imageViewBounds.getHeight()));
            imageStackPane.setMinWidth(imageViewBounds.getWidth());
            imageView.setFitWidth(0);
            imageView.setFitHeight(0);
        }
    }

    private void updateState()
    {
        nextButton.updateState();
        prevButton.updateState();
    }

    private Bounds searchMaxImageSize() {
        double maxHeight = 0d;
        double maxWidth = 0d;

        for(LearnCaseView learnCase : learnCases)
        {
            Optional<WritableImage> OptImage = learnCase.getImage();
            if(OptImage.isPresent())
            {
                WritableImage image = OptImage.get();
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
        return new BoundingBox(0, 0, maxWidth, maxHeight);
    }

    int getCurrentCaseIndex() {
        return currentCaseIndex;
    }

    void setCurrentCaseIndex(int currentCaseIndex) {
        this.currentCaseIndex = currentCaseIndex;
        updateState();
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
            aCaseView.getImage().ifPresent(image -> {
                imageView.setImage(image);
           });
        }
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
}
