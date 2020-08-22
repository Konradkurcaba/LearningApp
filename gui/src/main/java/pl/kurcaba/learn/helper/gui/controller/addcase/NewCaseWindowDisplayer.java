package pl.kurcaba.learn.helper.gui.controller.addcase;

import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.dialogs.AbstractWindowDisplayer;
import pl.kurcaba.learn.helper.gui.dialogs.ConfirmationStatus;

import java.nio.file.Path;
import java.util.Optional;

public class NewCaseWindowDisplayer extends AbstractWindowDisplayer<AddCaseWindowController> {

    private static final Path NEW_CASE_FXML = Path.of("fxml/add_new_case_panel.fxml");
    public static final String WINDOW_TITLE = "Dodawanie nowego pojęcia";

    public NewCaseWindowDisplayer() {
        super(WINDOW_TITLE);
    }

    @Override
    protected AddCaseWindowController createController() {
        return new AddCaseWindowController();
    }

    public NewCaseDto showNewCaseWindow() {

        Stage stage = prepareStage(NEW_CASE_FXML);
        stage.showAndWait();

        String name = getController().getNewCaseName();
        String definition = getController().getNewCaseDefinition();
        Optional<WritableImage> image = getController().getNewCasePicture();
        ConfirmationStatus status = getController().getUserAction();

        return new NewCaseDto(name, definition, image, status);
    }
}
