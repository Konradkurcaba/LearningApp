package pl.kurcaba.learn.helper.gui.addcase.controller;

import javafx.scene.image.WritableImage;
import pl.kurcaba.learn.helper.gui.screen.ConfirmationStatus;

import java.util.Optional;

public class NewCaseDto {

    private final String newCaseName;
    private final String newCaseDefinition;
    private final Optional<WritableImage> newCaseImage;
    private final ConfirmationStatus confirmationStatus;

    public NewCaseDto(String newCaseName, String newCaseDefinition, Optional<WritableImage> newCaseImage
            , ConfirmationStatus aStatus) {
        this.newCaseName = newCaseName;
        this.newCaseDefinition = newCaseDefinition;
        this.newCaseImage = newCaseImage;
        this.confirmationStatus = aStatus;
    }

    public String getNewCaseName() {
        return newCaseName;
    }

    public String getNewCaseDefinition() {
        return newCaseDefinition;
    }

    public Optional<WritableImage> getNewCaseImage() {
        return newCaseImage;
    }

    public ConfirmationStatus getConfirmationStatus() {
        return confirmationStatus;
    }
}
