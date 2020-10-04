package pl.kurcaba.learn.helper.gui.backend;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.WritableImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.screen.ImageConverter;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;
import pl.kurcaba.learn.helper.learnset.model.LearnCase;
import pl.kurcaba.learn.helper.learnset.model.LearnSet;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.learnset.values.NonUniqueException;
import pl.kurcaba.learn.helper.persistence.LearnSetDaoIf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GuiModelBroker {

    private static final Logger logger = LogManager.getLogger(GuiModelBroker.class);

    private final LearnSetDaoIf LearnSetDao;
    private LearnSet currentLearnSet;
    private final BooleanProperty hasUnsavedChanges = new SimpleBooleanProperty(false);
    private final BooleanProperty isLearnSetChosen  = new SimpleBooleanProperty(false);


    public GuiModelBroker(LearnSetDaoIf aLearnSetDao)
    {
        LearnSetDao = aLearnSetDao;
    }

    public void createNewLearnSet(LearnSetName learnSetName) throws IOException, NonUniqueException {
        currentLearnSet = LearnSetDao.createNewLearnSet(learnSetName);
        updateProperties();
    }

    public void createNewCase(String newCaseName, String newDefinition, WritableImage aImage) {
        if(currentLearnSet != null)
        {
            LearnCase learnCase = currentLearnSet.createNewCase(newCaseName, newDefinition);
            if(aImage != null)
            {
                try
                {
                    learnCase.setImage(ImageConverter.convertToByte(aImage));
                } catch (IOException e) {
                    logger.error("A problem has occurred:", e);
                }
            }
        }
        else
        {
            logger.warn("Cannot create new case because set manager is not set");
            throw new IllegalStateException("Set is not chosen by user");
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
        currentLearnSet = LearnSetDao.getSetByName(aSetName);
        updateProperties();
    }

    public void saveChanges() throws IOException {
        LearnSetDao.saveChanges(currentLearnSet);
        updateProperties();
    }

    public List<LearnSetName> getAllSetsNames() throws IOException {
        return LearnSetDao.getAllNames();
    }

    public List<LearnCaseView> getCaseViews() {
        List<LearnCaseView> caseViews = buildViewsForCurrentSet();

        for(LearnCaseView caseView: caseViews)
        {
            caseView.isUsedToLearnProperty().addListener(valueChanged -> isUsedToLearnPropertyChanged(caseView));
        }
        return caseViews;
    }

    private void isUsedToLearnPropertyChanged(LearnCaseView caseView)
    {
        boolean newValue = caseView.isUsedToLearnProperty().get();
        Optional<LearnCase> learnCase = currentLearnSet.getLearnSetCases().stream()
                .filter(s -> s.getId().equals(caseView.getId()))
                .findAny();
        learnCase.ifPresent(foundCase ->
        {
            foundCase.setUsedToLearn(newValue);
            hasUnsavedChanges.setValue(true);
        });
    }

    private List<LearnCaseView> buildViewsForCurrentSet()
    {
        if(currentLearnSet == null)
        {
            return new ArrayList<>();
        }
        return currentLearnSet.getLearnSetCases().stream().map(learnCase -> LearnCaseView.builder(learnCase.getId())
                .setName(learnCase.getName())
                .setDefinition(learnCase.getDefinition())
                .setImage(convertImage(learnCase.getImage()))
                .setUsedToLearn(learnCase.isUsedToLearn())
                .build())
                .collect(Collectors.toList());
    }

    public BooleanProperty getUnsavedChangesProperty()
    {
        return hasUnsavedChanges;
    }

    public BooleanProperty isLearnSetChosenProperty()
    {
        return isLearnSetChosen;
    }

    private void updateUnsavedChangesProperty()
    {
        if(currentLearnSet != null)
        {
            hasUnsavedChanges.setValue(currentLearnSet.hasUnsavedChanges());
        }else
        {
            hasUnsavedChanges.setValue(false);
        }
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
            logger.error("A problem has occurred:", aEx);
            return null;
        }
    }

    public void removeLearnSet(LearnSetName learnSet)
    {
        boolean isCurrentSetToRemove = learnSet.equals(currentLearnSet.getLearnSetName());
        if(isLearnSetChosenProperty().get() && isCurrentSetToRemove)
        {
            currentLearnSet = null;
            updateProperties();
        }
        LearnSetDao.remove(learnSet);
    }
}
