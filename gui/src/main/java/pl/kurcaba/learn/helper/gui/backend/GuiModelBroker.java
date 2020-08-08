package pl.kurcaba.learn.helper.gui.backend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;
import pl.kurcaba.learn.helper.learnset.model.LearnDataManager;
import pl.kurcaba.learn.helper.learnset.model.LearnSetManager;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.learnset.values.NonUniqueException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GuiModelBroker {

    private static final Logger logger = LogManager.getLogger(GuiModelBroker.class);

    private LearnDataManager dataManager;
    private LearnSetManager currentSetManager;

    public GuiModelBroker(LearnDataManager aDataManager) {
        dataManager = aDataManager;
    }

    public void createNewLearnSet(LearnSetName learnSetName) throws IOException, NonUniqueException {
        currentSetManager = dataManager.createNewLearnSet(learnSetName);
    }

    public void createNewCase(String newCaseName, String newDefinition) {
        if(currentSetManager != null)
        {
            currentSetManager.createNewCase(newCaseName, newDefinition);
        }else
        {
            logger.warn("Cannot create new case because set manager is not set");
        }
    }

    public boolean deleteCase(LearnCaseView view) {
        return currentSetManager.deleteCase(view.getId());
    }

    public void changeCurrentSet(LearnSetName aSetName) throws IOException, ClassNotFoundException {
        currentSetManager = dataManager.getManager(aSetName);
    }

    public void saveChanges() throws IOException {
        dataManager.save(currentSetManager);
    }

    public List<LearnSetName> getAllSetsNames() throws IOException {
        return dataManager.getAllSetsNames();
    }

    public List<LearnCaseView> getCaseViews() {
        return currentSetManager.getAllLearnCases().stream().map(learnCase -> LearnCaseView.builder(learnCase.getId())
                .setName(learnCase.getName())
                .setDefinition(learnCase.getDefinition())
                .build()).collect(Collectors.toList());
    }
}
