package pl.kurcaba.learn.helper.gui.dialogs.addcase;

import javafx.scene.image.WritableImage;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;

import java.util.List;
import java.util.Optional;

public class NewCaseDto {

    private final String newCaseName;
    private final String newCaseDefinition;
    private final List<WritableImage> newCaseImages;

    public NewCaseDto(String newCaseName, String newCaseDefinition, List<WritableImage> images)
    {
        this.newCaseName = newCaseName;
        this.newCaseDefinition = newCaseDefinition;
        this.newCaseImages = images;
    }

    public String getNewCaseName() {
        return newCaseName;
    }

    public String getNewCaseDefinition() {
        return newCaseDefinition;
    }

    public List<WritableImage> getNewCaseImages() {
        return newCaseImages;
    }

}
