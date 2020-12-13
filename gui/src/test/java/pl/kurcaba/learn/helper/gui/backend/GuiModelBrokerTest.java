package pl.kurcaba.learn.helper.gui.backend;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.kurcaba.learn.helper.common.model.LearnCase;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.model.LearnSetDaoIf;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.LearnSetNameFormatException;
import pl.kurcaba.learn.helper.common.values.NonUniqueException;
import pl.kurcaba.learn.helper.gui.dialogs.addcase.NewCaseDto;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.io.IOException;
import java.util.*;

class GuiModelBrokerTest
{

    private LearnSetDaoIf mockedDao;
    private LearnSetName testLearnSetName;
    private LearnSet testSet;
    GuiModelBroker modelBroker;

    //the second set, used when tests needs more than one set.
    LearnSetName secondSetName;
    LearnCase exampleCase;
    LearnSet secondLearnSet;

    @BeforeEach
    public void init() throws LearnSetNameFormatException, IOException, NonUniqueException
    {
        //create example data
        testLearnSetName = new LearnSetName("exampleLearnSet");
        testSet = new LearnSet(testLearnSetName, new TreeSet<>());

        //create DAO mock
        mockedDao = Mockito.mock(LearnSetDaoIf.class);
        Mockito.when(mockedDao.createNewLearnSet(testLearnSetName)).thenReturn(testSet);

        //create GuiModelBroker with mocked Dao
        modelBroker = new GuiModelBroker(mockedDao);

        //initialization of the second set, used when tests needs more than one set.
        secondSetName = new LearnSetName("secondSet");
        exampleCase = new LearnCase("secondCase", "secondDefinition");
        secondLearnSet = new LearnSet(secondSetName, new TreeSet<>());
    }


    @Test
    public void oneCaseIsCreatedAfterCreatingOneCase() throws IOException, NonUniqueException
    {
        //set up a test
        modelBroker.createNewLearnSet(testLearnSetName);

        //a real test
        modelBroker.createNewCase("caseName", "caseDefinition");
        List<LearnCaseView> ceaseViews = modelBroker.getCaseViews();

        //Assertion
        Assertions.assertEquals(1, ceaseViews.size());
    }

    @Test
    public void correctCaseIsCreated() throws IOException, NonUniqueException
    {

        //set up a test
        modelBroker.createNewLearnSet(testLearnSetName);

        //a real test
        modelBroker.createNewCase("caseName", "caseDefinition");
        Optional<LearnCaseView> caseView = modelBroker.getCaseViews().stream().findAny();

        //Assertions
        Assertions.assertTrue(caseView.isPresent());
        Assertions.assertEquals("caseName", caseView.get().getName());
        Assertions.assertEquals("caseDefinition", caseView.get().getDefinition());
    }

    @Test
    public void exceptionIsThrownIsLearnSetIsNotChosen() throws IOException, NonUniqueException
    {

        //a real test
        Assertions.assertThrows(IllegalStateException.class, () -> modelBroker.createNewCase("caseName", "caseDefinition"));
    }

    @Test
    public void isImageCreatedCorrectly() throws IOException, NonUniqueException
    {

        //set up a test
        modelBroker.createNewLearnSet(testLearnSetName);
        WritableImage testImage = new WritableImage(1, 1);
        testImage.getPixelWriter().setColor(0, 0, Color.ANTIQUEWHITE);

        //a real test
        modelBroker.createNewCase("caseName", "caseDefinition", Arrays.asList(testImage));
        LearnCaseView caseView = modelBroker.getCaseViews().get(0);

        //Assertion
        WritableImage resultImage = caseView.getImages().get(0);

        Assertions.assertEquals(testImage.getPixelReader().getColor(0, 0), resultImage.getPixelReader().getColor(0, 0));
        Assertions.assertEquals(testImage.getHeight(), resultImage.getHeight());
        Assertions.assertEquals(testImage.getWidth(), resultImage.getWidth());
    }

    @Test
    public void unsavedChangesPropertyReturnsTrueIfUserDoesNotSaveChanges() throws IOException, NonUniqueException
    {
        //set up a test
        modelBroker.createNewLearnSet(testLearnSetName);
        Mockito.doAnswer(invocationOnMock -> {
            ((LearnSet) invocationOnMock.getArgument(0)).setSaved();
            return null;
        }).when(mockedDao).saveChanges(Mockito.any());

        //a real test
        modelBroker.createNewCase("caseName", "caseDefinition");

        //Assertions
        Assertions.assertTrue(modelBroker.getUnsavedChangesProperty().get());
    }

    @Test
    public void unsavedChangesPropertyReturnsFalseIfUserSavedChanges() throws IOException, NonUniqueException
    {
        //set up a test
        modelBroker.createNewLearnSet(testLearnSetName);
        Mockito.doAnswer(invocationOnMock -> {
            ((LearnSet) invocationOnMock.getArgument(0)).setSaved();
            return null;
        }).when(mockedDao).saveChanges(Mockito.any());

        //a real test
        modelBroker.createNewCase("caseName", "caseDefinition");
        modelBroker.saveChanges();

        //Assertions
        Assertions.assertFalse(modelBroker.getUnsavedChangesProperty().get());
    }

    @Test
    public void setChosenPropertyIsFalseWhenSetIsNotChosen() throws IOException, NonUniqueException
    {
        //set up a test
        GuiModelBroker modelBroker = new GuiModelBroker(mockedDao);

        //a real test
        Assertions.assertFalse(modelBroker.isLearnSetChosenProperty().get());
    }

    @Test
    public void setChosenPropertyIsTrueWhenSetIsChosen() throws IOException, NonUniqueException
    {
        //set up a test
        GuiModelBroker modelBroker = new GuiModelBroker(mockedDao);

        //a real test
        modelBroker.createNewLearnSet(testLearnSetName);

        //Assertion
        Assertions.assertTrue(modelBroker.isLearnSetChosenProperty().get());
    }

    @Test
    public void setChosenPropertyIsFalseAfterDeletingCurrentLearnSet() throws IOException, NonUniqueException
    {
        //set up a test
        GuiModelBroker modelBroker = new GuiModelBroker(mockedDao);

        //a real test
        modelBroker.createNewLearnSet(testLearnSetName);
        modelBroker.removeLearnSet(testLearnSetName);

        //Assertion
        Assertions.assertFalse(modelBroker.isLearnSetChosenProperty().get());
    }

    @Test
    public void LearnCasesAreChangedWhenSetIsChanged() throws IOException, NonUniqueException, LearnSetNameFormatException, ClassNotFoundException
    {
        //set up a test
        secondLearnSet.addLearnCase(exampleCase);
        Mockito.when(mockedDao.getSetByName(secondSetName)).thenReturn(secondLearnSet);
        testSet.addLearnCase(new LearnCase("testCase", "TestCaseDefinition"));

        //a real test
        modelBroker.createNewLearnSet(testLearnSetName);
        LearnCaseView oldCaseView = modelBroker.getCaseViews().get(0);
        modelBroker.changeCurrentSet(secondSetName);

        //Assertions
        Assertions.assertEquals(1, modelBroker.getCaseViews().size());
        Assertions.assertNotEquals(oldCaseView, modelBroker.getCaseViews().get(0));
    }

    @Test
    public void hasUnsavedPropertyIsUpdatedAfterChangingCurrentLearnSet() throws IOException, NonUniqueException, ClassNotFoundException
    {
        //set up a test
        secondLearnSet.addLearnCase(exampleCase);
        secondLearnSet.setSaved();
        Mockito.when(mockedDao.getSetByName(secondSetName)).thenReturn(secondLearnSet);
        testSet.addLearnCase(new LearnCase("testCase", "TestCaseDefinition"));

        //a real test
        modelBroker.createNewLearnSet(testLearnSetName);
        modelBroker.createNewCase("exampleName", "exampleDefinition");
        modelBroker.changeCurrentSet(secondSetName);

        //Assertions
        Assertions.assertFalse(modelBroker.getUnsavedChangesProperty().get());
    }

    @Test
    public void changingIsUsedToLearnPropertyShouldUpdateModel() throws IOException, NonUniqueException
    {
        //set up a test
        testSet.addLearnCase(new LearnCase("testCase", "TestCaseDefinition"));
        modelBroker.createNewLearnSet(testLearnSetName);
        LearnCaseView learnCaseView = modelBroker.getCaseViews().get(0);

        //a real test
        learnCaseView.isUsedToLearnProperty().setValue(false);

        //assertion
        Assertions.assertFalse(testSet.getLearnSetCases().get(0).isUsedToLearn());

        //a real test
        learnCaseView.isUsedToLearnProperty().setValue(true);

        //assertion
        Assertions.assertTrue(testSet.getLearnSetCases().get(0).isUsedToLearn());
    }

    @Test
    public void editedCaseShouldBeAltered() throws IOException, NonUniqueException
    {
        //set up a test
        testSet.addLearnCase(new LearnCase("testCase", "definition"));
        modelBroker.createNewLearnSet(testLearnSetName);
        LearnCaseView learnCaseView = modelBroker.getCaseViews().get(0);
        NewCaseDto newCaseDto = new NewCaseDto("editedName", "editedDefinition"
                , new ArrayList<>(), ConfirmationStatus.CONFIRMED);
        //a real test
        modelBroker.editCase(learnCaseView.getId(), newCaseDto);

        //assertions
        LearnCaseView editedView = modelBroker.getCaseViews().get(0);

        Assertions.assertEquals("editedName", editedView.getName());
        Assertions.assertEquals("editedDefinition", editedView.getDefinition());
        Assertions.assertTrue(editedView.getImages().isEmpty());
    }

    @Test
    public void editedCaseShouldBeInCorrectPlace() throws IOException, NonUniqueException, InterruptedException
    {
        //set up a test
        testSet.addLearnCase(new LearnCase("testCase0", "definition0"));
        Thread.sleep(2);
        testSet.addLearnCase(new LearnCase("testCase1", "definition1"));
        Thread.sleep(2);
        testSet.addLearnCase(new LearnCase("testCase2", "definition2"));
        modelBroker.createNewLearnSet(testLearnSetName);
        LearnCaseView learnCaseView = modelBroker.getCaseViews().get(1);
        NewCaseDto newCaseDto = new NewCaseDto("editedName", "editedDefinition"
                , new ArrayList<>(), ConfirmationStatus.CONFIRMED);

        //a real test
        modelBroker.editCase(learnCaseView.getId(), newCaseDto);

        //assertions
        LearnCaseView editedView = modelBroker.getCaseViews().get(1);
        Assertions.assertEquals("editedName", editedView.getName());
    }


}