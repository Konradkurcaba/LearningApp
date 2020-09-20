package pl.kurcaba.learn.helper.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.controller.main.CommandIf;
import pl.kurcaba.learn.helper.gui.controlls.CommandButton;

import java.io.InputStream;

public abstract class AbstractWindowController
{
    private static final Logger logger = LogManager.getLogger(AbstractWindowController.class);

    public static final String GRAY_CROSS_PATH = "images/gray-cross.png";
    public static final String WHITE_CROSS_PATH = "images/white-cross.png";

    @FXML
    private CommandButton exitButton;

    @FXML
    private ImageView exitImageView;

    @FXML
    private Region topRegion;

    @FXML
    public void initialize()
    {
        try
        {
            initExitButton();
            initTopRegion();
        }
        catch (NullPointerException aEx)
        {
            logger.error("A problem has occurred:", aEx);
        }
    }

    private Stage stage;

    private void initExitButton()
    {
        Image exitImage = getImage(GRAY_CROSS_PATH);
        exitImageView.setImage(exitImage);

        exitButton.setOnMouseEntered(event -> {
            Image whiteExitImage = getImage(WHITE_CROSS_PATH);
            exitImageView.setImage(whiteExitImage);
        });

        exitButton.setOnMouseExited(event ->{
            Image grayExitImage = getImage(GRAY_CROSS_PATH);
            exitImageView.setImage(grayExitImage);
        });
    }

    void initTopRegion()
    {
        topRegion.setOnMousePressed(pressEvent -> {
            topRegion.setOnMouseDragged(dragEvent -> {
                getStage().setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                getStage().setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
    }

    protected Image getImage(String aPath)
    {
        InputStream imageStream = this.getClass().getClassLoader().getResourceAsStream(aPath);
        return new Image(imageStream);
    }

    public void setStage(Stage aStage)
    {
        stage = aStage;
        exitButton.setCommand(createExitCommand(aStage));
    }

    protected CommandIf createExitCommand(Stage aStage)
    {
        return new CloseWindowCommand(aStage);
    }

    protected Stage getStage()
    {
        return stage;
    }

    protected void killThisWindow() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
