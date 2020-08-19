package pl.kurcaba.learn.helper.gui.controller.main;

import javafx.scene.image.WritableImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.controller.addcase.NewCaseDto;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.screen.ConfirmationStatus;

import java.io.IOException;
import java.util.Optional;

public class AddCaseCommand extends MainWindowCommand
{

    private static final Logger logger = LogManager.getLogger(AddCaseCommand.class);

    public AddCaseCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {

        try {
            NewCaseDto newCaseDto = windowController.showNewCaseWindow();
            if (newCaseDto.getConfirmationStatus().equals(ConfirmationStatus.CONFIRMED)) {
                String newCaseName = newCaseDto.getNewCaseName();
                String newDefinition = newCaseDto.getNewCaseDefinition();
                Optional<WritableImage> newScreenShot = newCaseDto.getNewCaseImage();

                newScreenShot.ifPresentOrElse(writableImage -> guiModelBroker.createNewCase(newCaseName, newDefinition, writableImage)
                        , () -> guiModelBroker.createNewCase(newCaseName, newDefinition));

                windowController.refreshSetData();
            }
        } catch (IOException e) {
            logger.error(e);
        }

    }
}
