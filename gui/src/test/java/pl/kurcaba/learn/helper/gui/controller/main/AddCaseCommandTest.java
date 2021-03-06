package pl.kurcaba.learn.helper.gui.controller.main;


import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.dialogs.addcase.NewCaseDto;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;

import java.util.Optional;

class AddCaseCommandTest
{

    GuiModelBroker mockedBroker = Mockito.mock(GuiModelBroker.class);
    MainWindowController mainController = Mockito.mock(MainWindowController.class);


    @Test
    public void shouldCreateNewCaseWhenUserConfirm()
    {
        //set up a test
        NewCaseDto exampleDto = new NewCaseDto("exampleName"
                , "exampleDefinition", Optional.empty(), ConfirmationStatus.CONFIRMED);
        Mockito.when(mainController.showNewCaseWindow()).thenReturn(exampleDto);
        AddCaseCommand addCaseCommand = new AddCaseCommand(mockedBroker, mainController);

        //a real test
        addCaseCommand.executeCommand();

        //Assertion
        Mockito.verify(mockedBroker).createNewCase("exampleName"
                , "exampleDefinition");
    }

    @Test
    public void shouldNotCreateNewCaseWhenUserConfirm()
    {
        //set up a test
        NewCaseDto exampleDto = new NewCaseDto("exampleName"
                , "exampleDefinition", Optional.empty(), ConfirmationStatus.REJECTED);
        Mockito.when(mainController.showNewCaseWindow()).thenReturn(exampleDto);
        AddCaseCommand addCaseCommand = new AddCaseCommand(mockedBroker, mainController);

        //a real test
        addCaseCommand.executeCommand();

        //Assertion
        Mockito.verify(mockedBroker, Mockito.never()).createNewCase(Mockito.any(), Mockito.any());
    }

    @Test
    public void shouldCreateCaseWithImageIfImageWasChosenByUser()
    {
        //set up a test
        WritableImage image = new WritableImage(10, 10);
        NewCaseDto exampleDto = new NewCaseDto("exampleName"
                , "exampleDefinition", Optional.of(image), ConfirmationStatus.CONFIRMED);
        Mockito.when(mainController.showNewCaseWindow()).thenReturn(exampleDto);
        AddCaseCommand addCaseCommand = new AddCaseCommand(mockedBroker, mainController);

        //a real test
        addCaseCommand.executeCommand();

        //Assertion
        Mockito.verify(mockedBroker).createNewCase("exampleName"
                , "exampleDefinition", image);
    }

    @Test
    public void shouldCreateCaseWithoutImageIfImageWasNotChosenByUser()
    {
        //set up a test
        NewCaseDto exampleDto = new NewCaseDto("exampleName"
                ,  "exampleDefinition", Optional.empty(), ConfirmationStatus.CONFIRMED);
        Mockito.when(mainController.showNewCaseWindow()).thenReturn(exampleDto);
        AddCaseCommand addCaseCommand = new AddCaseCommand(mockedBroker, mainController);

        //a real test
        addCaseCommand.executeCommand();

        //Assertions
        Mockito.verify(mockedBroker).createNewCase("exampleName"
                , "exampleDefinition");
        Mockito.verify(mockedBroker, Mockito.never()).createNewCase(Mockito.anyString()
                , Mockito.anyString(), Mockito.any());
    }
}