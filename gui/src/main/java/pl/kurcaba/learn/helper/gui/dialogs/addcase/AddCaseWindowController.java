package pl.kurcaba.learn.helper.gui.dialogs.addcase;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import pl.kurcaba.learn.helper.gui.controller.AbstractWindowController;
import pl.kurcaba.learn.helper.gui.controller.CloseWindowCommand;
import pl.kurcaba.learn.helper.gui.controlls.CommandButton;

import java.util.List;
import java.util.Optional;

public class AddCaseWindowController extends AbstractWindowController
{

    @FXML
    protected TextField nameTf;

    @FXML
    protected TextField definitionTf;

    @FXML
    private CommandButton makeScreenButton;

    @FXML
    private CommandButton removeScreenButton;

    @FXML
    private CommandButton okButton;

    @FXML
    private CommandButton cancelButton;

    @FXML
    private ListView<ImageWithCounterWrapper> imageListView;

    private final ObservableList<WritableImage> screenshots = FXCollections.observableArrayList();
    private int imageCounter = 1;

    private NewCaseDto createdDto;

    @FXML
    public void initialize()
    {
        super.initialize();
        initButtons();
        initListView();
    }

    private void initListView()
    {
        imageListView.setCellFactory(listView -> new ImageListCell(new ShowImageCommand(this)));
        imageListView.getFocusModel().focusedIndexProperty().addListener(focusedIndex -> removeScreenButton.updateState());
        imageListView.getItems().addListener((InvalidationListener) itemsChanged -> removeScreenButton.updateState());
    }

    private void initButtons()
    {
        okButton.setCommand(new ConfirmAddCaseWindowCmd(this));
        cancelButton.setCommand(new CloseWindowCommand(this));
        makeScreenButton.setCommand(new AddScreenshotCmd(this));
        removeScreenButton.setCommand(new RemoveScreenshotCmd(this));
        removeScreenButton.updateState();
    }

    void addScreenshot(WritableImage aScreenshot)
    {
        screenshots.add(aScreenshot);
        imageListView.getItems().add(new ImageWithCounterWrapper(aScreenshot, imageCounter++));
        imageListView.getFocusModel().focus(screenshots.size() - 1);
    }

    void removeFocusedScreenshot()
    {
        ImageWithCounterWrapper focusedImage = imageListView.getFocusModel().getFocusedItem();
        screenshots.remove(focusedImage.getImage());
        imageListView.getItems().remove(focusedImage);
    }

    boolean isAnyScreenshotSelected()
    {
        return imageListView.getFocusModel().getFocusedIndex() != -1;
    }
    WritableImage getSelectedImage()
    {
        return imageListView.getFocusModel().getFocusedItem().getImage();
    }

    public String getNewCaseName() {
        return nameTf.getText();
    }

    public String getNewCaseDefinition()
    {
        return definitionTf.getText();
    }

    public List<WritableImage> getScreenshots()
    {
        return screenshots;
    }

    protected void addScreenshots(List<WritableImage> screenshots)
    {
        screenshots.forEach(this::addScreenshot);
    }

    public Optional<NewCaseDto> getCreatedCase()
    {
        return Optional.ofNullable(createdDto);
    }

    public void setCreatedDto(NewCaseDto createdDto)
    {
        this.createdDto = createdDto;
    }
}
