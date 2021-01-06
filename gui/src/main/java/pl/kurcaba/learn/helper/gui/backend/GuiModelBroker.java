package pl.kurcaba.learn.helper.gui.backend;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.WritableImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.common.model.LearnCase;
import pl.kurcaba.learn.helper.common.model.LearnSet;
import pl.kurcaba.learn.helper.common.model.LearnSetDaoIf;
import pl.kurcaba.learn.helper.common.values.LearnSetName;
import pl.kurcaba.learn.helper.common.values.NonUniqueException;
import pl.kurcaba.learn.helper.gui.dialogs.addcase.NewCaseDto;
import pl.kurcaba.learn.helper.gui.screen.ImageConverter;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GuiModelBroker
{

    private static final Logger logger = LogManager.getLogger(GuiModelBroker.class);

    private final LearnSetDaoIf LearnSetDao;
    private LearnSet currentLearnSet;
    private final BooleanProperty hasUnsavedChanges = new SimpleBooleanProperty(false);
    private final BooleanProperty isLearnSetChosen = new SimpleBooleanProperty(false);

    private final LearnSetCache cache = new LearnSetCache();

    public GuiModelBroker(LearnSetDaoIf aLearnSetDao)
    {
        LearnSetDao = aLearnSetDao;
    }

    public void createNewLearnSet(LearnSetName learnSetName) throws IOException, NonUniqueException
    {
        currentLearnSet = LearnSetDao.createNewLearnSet(learnSetName);
        updateProperties();
    }

    public void createNewCase(String newCaseName, String newDefinition, List<WritableImage> aImages)
    {
        if (currentLearnSet != null)
        {
            LearnCase learnCase = currentLearnSet.createNewCase(newCaseName, newDefinition);
            learnCase.setImages(convertImagesToBytes(aImages));
        }
        else
        {
            logger.warn("Cannot create new case because set manager is not set");
            throw new IllegalStateException("Set is not chosen by user");
        }
        updateProperties();
    }

    public void editCase(UUID aCaseId, NewCaseDto editedDto)
    {
        List<byte[]> convertedImages = convertImagesToBytes(editedDto.getNewCaseImages());
        currentLearnSet.editCase(aCaseId, editedDto.getNewCaseName(), editedDto.getNewCaseDefinition()
                ,convertedImages);
        updateProperties();
    }

    public void createNewCase(String newCaseName, String newDefinition)
    {
        createNewCase(newCaseName, newDefinition, new ArrayList<>());
        updateProperties();
    }


    public boolean deleteCase(LearnCaseView view)
    {
        boolean removeStatus = currentLearnSet.removeCase(view.getId());
        updateProperties();
        return removeStatus;
    }

    public void changeCurrentSet(LearnSetName aSetName) throws IOException, ClassNotFoundException
    {
        if(cache.isCached(aSetName))
        {
            cache.get(aSetName).ifPresent(learnSet -> currentLearnSet = learnSet);
        }
        else
        {
            currentLearnSet = LearnSetDao.getSetByName(aSetName);
            cache.put(currentLearnSet);
        }
        updateProperties();
    }

    public void saveChanges() throws IOException
    {
        cache.put(currentLearnSet);
        currentLearnSet = LearnSetDao.saveChanges(currentLearnSet);
        updateProperties();
    }

    public List<LearnSetName> getAllSetsNames() throws IOException
    {
        return LearnSetDao.getAllNames();
    }

    public List<LearnCaseView> getCaseViews()
    {
        List<LearnCaseView> caseViews = buildViewsForCurrentSet();

        for (LearnCaseView caseView : caseViews)
        {
            caseView.isUsedToLearnProperty().addListener(valueChanged -> isUsedToLearnPropertyChanged(caseView));
        }
        return caseViews;
    }

    private void isUsedToLearnPropertyChanged(LearnCaseView caseView)
    {
        boolean newValue = caseView.isUsedToLearnProperty().get();
        Optional<LearnCase> learnCase = currentLearnSet.getLearnSetCases().stream()
                .filter(s -> s.getUuid().equals(caseView.getId().toString()))
                .findAny();
        learnCase.ifPresent(foundCase ->
        {
            foundCase.setUsedToLearn(newValue);
            hasUnsavedChanges.setValue(true);
        });
    }

    private List<LearnCaseView> buildViewsForCurrentSet()
    {
        if (currentLearnSet == null)
        {
            return new ArrayList<>();
        }
        return currentLearnSet.getLearnSetCases().stream().map(learnCase -> LearnCaseView.builder(UUID.fromString(learnCase.getUuid()))
                .setName(learnCase.getName())
                .setDefinition(learnCase.getDefinition())
                .setImages(convertImages(learnCase.getImages()))
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
        if (currentLearnSet != null)
        {
            hasUnsavedChanges.setValue(currentLearnSet.hasUnsavedChanges());
        }
        else
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

    private List<WritableImage> convertImages(List<byte[]> aImages)
    {
            return aImages.stream()
                    .map(ImageConverter::convertToImage)
                    .collect(Collectors.toList());
    }

    private List<byte[]> convertImagesToBytes(List<WritableImage> aImages)
    {
        return aImages.stream()
                .map(ImageConverter::convertToByte)
                .collect(Collectors.toList());
    }


    public void removeLearnSet(LearnSetName learnSet)
    {
        boolean isCurrentSetToRemove = learnSet.equals(currentLearnSet.getLearnSetName());
        if (isLearnSetChosenProperty().get() && isCurrentSetToRemove)
        {
            currentLearnSet = null;
            updateProperties();
        }
        try
        {
            LearnSetDao.remove(learnSet);
        } catch (IOException aEx)
        {
            logger.error("Cannot remove learnSet file, an exception has occur", aEx);
        }
    }
}
