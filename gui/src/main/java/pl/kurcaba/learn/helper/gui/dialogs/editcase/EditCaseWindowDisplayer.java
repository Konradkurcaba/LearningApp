package pl.kurcaba.learn.helper.gui.dialogs.editcase;

import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowDisplayer;
import pl.kurcaba.learn.helper.gui.dialogs.addcase.NewCaseDto;
import pl.kurcaba.learn.helper.gui.dialogs.addcase.NewCaseWindowDisplayer;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.util.Optional;

public class EditCaseWindowDisplayer extends AbstractWindowDisplayer<EditCaseWindowController>
{
    @Override
    protected EditCaseWindowController createController()
    {
        return new EditCaseWindowController();
    }

    public NewCaseDto showEditCaseWindow(LearnCaseView aCaseView) {

        Stage stage = prepareStage(NewCaseWindowDisplayer.NEW_CASE_FXML);
        getController().fillFields(aCaseView);
        stage.showAndWait();

        String name = getController().getNewCaseName();
        String definition = getController().getNewCaseDefinition();
        Optional<WritableImage> image = getController().getNewCasePicture();
        ConfirmationStatus status = getController().getUserAction();

        return new NewCaseDto(name, definition, image, status);
    }
}
