package pl.kurcaba.learn.helper.gui.backend;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;
import pl.kurcaba.learn.helper.learnset.model.LearnCase;
import pl.kurcaba.learn.helper.learnset.model.LearnDataManager;
import pl.kurcaba.learn.helper.learnset.model.LearnSetManager;
import pl.kurcaba.learn.helper.learnset.values.LearnSetName;
import pl.kurcaba.learn.helper.learnset.values.NonUniqueException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

    public void createNewCase(String newCaseName, String newDefinition, WritableImage aImage) {
        if(currentSetManager != null)
        {
            LearnCase learnCase = currentSetManager.createNewCase(newCaseName, newDefinition);
            if(aImage != null) {
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(aImage, null);
                try {
                    ImageIO.write(bufferedImage, "png", byteStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                learnCase.setImage(byteStream.toByteArray());
            }
        }else
        {
            logger.warn("Cannot create new case because set manager is not set");
        }
    }

    public void createNewCase(String newCaseName, String newDefinition) {
        createNewCase(newCaseName, newDefinition, null);
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
