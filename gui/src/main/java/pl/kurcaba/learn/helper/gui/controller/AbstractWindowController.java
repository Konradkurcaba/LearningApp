package pl.kurcaba.learn.helper.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import pl.kurcaba.learn.helper.gui.controlls.CommandButton;

import java.io.InputStream;

public abstract class AbstractWindowController
{
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
        initExitButton();
        initTopRegion();
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

    private Image getImage(String aPath)
    {
        InputStream imageStream = this.getClass().getClassLoader().getResourceAsStream(aPath);
        return new Image(imageStream);
    }

    protected void setStage(Stage aStage)
    {
        stage = aStage;
        exitButton.setCommand(new CloseWindowCommand(aStage));
    }

    protected Stage getStage()
    {
        return stage;
    }
}
