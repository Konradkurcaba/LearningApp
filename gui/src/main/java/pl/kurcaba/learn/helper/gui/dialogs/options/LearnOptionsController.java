package pl.kurcaba.learn.helper.gui.dialogs.options;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowController;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;

import java.util.Optional;

public class LearnOptionsController extends AbstractWindowController
{
    @FXML
    private CheckBox nameBox;
    @FXML
    private CheckBox definitionBox;
    @FXML
    private CheckBox imageBox;
    @FXML
    private CheckBox randomBox;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;


    private ConfirmationStatus status = ConfirmationStatus.REJECTED;

    @Override
    public void initialize()
    {
        super.initialize();
        initButtons();
        initCheckBoxes();
    }

    private void initCheckBoxes()
    {
        imageBox.setSelected(true);
        nameBox.setSelected(true);
        randomBox.setSelected(true);
    }

    private void initButtons() {
        okButton.setOnMouseClicked(mouseEvent -> {
            status = ConfirmationStatus.CONFIRMED;
            killThisWindow();
        });
        cancelButton.setOnMouseClicked(mouseEvent -> {
            status = ConfirmationStatus.REJECTED;
            killThisWindow();
        });
    }

    public ConfirmationStatus getStatus()
    {
        return status;
    }

    public Optional<LearnOptions> getLearnOptions()
    {
        if (status.equals(ConfirmationStatus.REJECTED))
        {
            return Optional.empty();
        } else
        {
            boolean showName = nameBox.isSelected();
            boolean showDefinition = definitionBox.isSelected();
            boolean showImage = imageBox.isSelected();
            boolean randomOrder = randomBox.isSelected();
            return Optional.of(new LearnOptions(showName, showDefinition, showImage, randomOrder));
        }
    }
}
