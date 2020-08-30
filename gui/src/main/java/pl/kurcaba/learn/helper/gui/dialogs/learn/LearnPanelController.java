package pl.kurcaba.learn.helper.gui.dialogs.learn;

import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowController;
import pl.kurcaba.learn.helper.gui.controlls.CommandButton;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.util.List;
import java.util.Optional;


public class LearnPanelController extends AbstractWindowController
{
    @FXML
    private CommandButton checkButton;

    @FXML
    private TextField nameTf;

    @FXML
    private TextField definitionTf;

    @FXML
    private ImageView imageView;

    @FXML
    private CommandButton prevButton;

    @FXML
    private CommandButton nextButton;


    final boolean isImageDisplayed;
    final boolean isNameDisplayed;
    final boolean isDefinitionDisplayed;

    final List<LearnCaseView> learnCases;
    private int currentCaseIndex;

    public LearnPanelController(boolean isImageDisplayed, boolean isNameDisplayed
            , boolean isDefinitionDisplayed, List<LearnCaseView> learnCases) {
        this.isImageDisplayed = isImageDisplayed;
        this.isNameDisplayed = isNameDisplayed;
        this.isDefinitionDisplayed = isDefinitionDisplayed;
        this.learnCases = learnCases;
    }

    @Override
    public void initialize() {
        super.initialize();
        initImageView();
        initButtons();
    }

    private void initButtons()
    {
        nextButton.setCommand(new NextCaseCmd(this));
        prevButton.setCommand(new PrevCaseCmd(this));
    }

    private void initImageView() {
        if(isImageDisplayed)
        {
            Bounds imageViewBounds = lookForMaxImageSize();
            imageView.setFitWidth(imageViewBounds.getWidth());
            imageView.setFitHeight(imageViewBounds.getHeight());
        }
    }

    private void updateState()
    {
        nextButton.updateState();
        prevButton.updateState();
    }

    private Bounds lookForMaxImageSize() {
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

    public int getCurrentCaseIndex() {
        return currentCaseIndex;
    }

    public void setCurrentCaseIndex(int currentCaseIndex) {
        this.currentCaseIndex = currentCaseIndex;
    }

    void displayCase(LearnCaseView aCaseView)
    {
        if(isNameDisplayed)
        {
            nameTf.setText(aCaseView.getName());
            nameTf.setEditable(false);
        }
        if(isDefinitionDisplayed)
        {
            definitionTf.setText(aCaseView.getDefinition());
            definitionTf.setEditable(false);
        }

        if(isImageDisplayed)
        {
            aCaseView.getImage().ifPresent(image -> {
                imageView.setImage(image);
           });
        }
    }
}
