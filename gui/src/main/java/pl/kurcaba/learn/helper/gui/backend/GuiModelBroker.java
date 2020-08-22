package pl.kurcaba.learn.helper.gui.backend;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.WritableImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.screen.ImageConverter;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;
import pl.kurcaba.learn.helper.learnset.model.LearnCase;
import pl.kurcaba.learn.helper.learnset.model.LearnDataManager;
import pl.kurcaba.learn.helper.learnset.model.LearnSet;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.learnset.values.NonUniqueException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GuiModelBroker {

    private static final Logger logger = LogManager.getLogger(GuiModelBroker.class);

    private final LearnDataManager dataManager;
    private LearnSet currentLearnSet;
    private final BooleanProperty hasUnsavedChanges = new SimpleBooleanProperty(false);
    private final BooleanProperty isLearnSetChosen  = new SimpleBooleanProperty(false);

    public GuiModelBroker(LearnDataManager aDataManager) {
        dataManager = aDataManager;
    }

    public void createNewLearnSet(LearnSetName learnSetName) throws IOException, NonUniqueException {
        currentLearnSet = dataManager.createNewLearnSet(learnSetName);
        updateProperties();
    }

    public void createNewCase(String newCaseName, String newDefinition, WritableImage aImage) {
        if(currentLearnSet != null)
        {
            LearnCase learnCase = currentLearnSet.createNewCase(newCaseName, newDefinition);
            if(aImage != null) {
                try {
                    learnCase.setImage(ImageConverter.convertToByte(aImage));
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }else
        {
            logger.warn("Cannot create new case because set manager is not set");
        }
        updateProperties();
    }

    public void createNewCase(String newCaseName, String newDefinition) {
        createNewCase(newCaseName, newDefinition, null);
        updateProperties();
    }


    public boolean deleteCase(LearnCaseView view)
    {
        boolean removeStatus = currentLearnSet.removeCase(view.getId());
        updateProperties();
        return removeStatus;
    }

    public void changeCurrentSet(LearnSetName aSetName) throws IOException, ClassNotFoundException {
        currentLearnSet = dataManager.getLearnSet(aSetName);
        updateProperties();
    }

    public void saveChanges() throws IOException {
        dataManager.save(currentLearnSet);
        updateProperties();
    }

    public List<LearnSetName> getAllSetsNames() throws IOException {
        return dataManager.getAllSetsNames();
    }

    public List<LearnCaseView> getCaseViews() {
        return currentLearnSet.getLearnSetCases().stream().map(learnCase -> LearnCaseView.builder(learnCase.getId())
                .setName(learnCase.getName())
                .setDefinition(learnCase.getDefinition())
                .setImage(convertImage(learnCase.getImage()))
                .build()).collect(Collectors.toList());
    }

    public BooleanProperty getUnsavedChangesProperty()
    {
        return hasUnsavedChanges;
    }

    public BooleanProperty getIsLearnSetChosenProperty()
    {
        return isLearnSetChosen;
    }

    private void updateUnsavedChangesProperty()
    {
        hasUnsavedChanges.setValue(currentLearnSet.hasUnsavedChanges());
    }

    private void updateIsLearnSetChosenProperty()
    {
        isLearnSetChosen.setValue(currentLearnSet != null);
    }

    private void updateProperties()
    {
        updateUnsavedChangesProperty();
        updateIsLearnSetChosenProperty();
    }

    private WritableImage convertImage(byte[] aImage) {
        try {
            if (aImage != null) {
                return ImageConverter.convertToImage(aImage);
            } else {
                return null;
            }
        } catch (IOException aEx) {
            logger.error(aEx);
            return null;
        }
    }
}
