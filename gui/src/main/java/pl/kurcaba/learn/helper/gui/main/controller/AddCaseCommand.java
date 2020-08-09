package pl.kurcaba.learn.helper.gui.main.controller;

import javafx.scene.image.WritableImage;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;

import java.util.Optional;

public class AddCaseCommand extends AbstractCommand {


    public AddCaseCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        String newCaseName = windowController.getNameFieldText();
        String newDefinition = windowController.getDefinitionFieldText();
        Optional<WritableImage> newScreenShot = windowController.getLastScreenShot();

        newScreenShot.ifPresentOrElse(writableImage -> guiModelBroker.createNewCase(newCaseName, newDefinition, writableImage)
                , () -> guiModelBroker.createNewCase(newCaseName, newDefinition));

        windowController.refreshSetData();
    }
}
