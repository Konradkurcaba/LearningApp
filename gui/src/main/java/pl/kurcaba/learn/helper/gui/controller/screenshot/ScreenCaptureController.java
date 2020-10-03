package pl.kurcaba.learn.helper.gui.controller.screenshot;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.screen.SnippingField;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class ScreenCaptureController
{

    private static final int RED = 1;
    private static final int GREEN = 0;
    private static final int BLUE = 0;
    private static final double TRANSPARENCY = 0.1;

    private Stage redBackgroundStage;
    private Pane rootGroup;
    private Scene scene;
    private WritableImage screenShot;
    private Logger LOG = LogManager.getLogger(ScreenCaptureController.class);

    public Optional<WritableImage> openScreenshotWindow()
    {
        redBackgroundStage = new Stage();
        rootGroup = new Pane();
        scene = new Scene(rootGroup, Color.TRANSPARENT);
        screenShot = null;

        redBackgroundStage.setScene(scene);
        scene.getRoot().setStyle("-fx-background-color: rgba(255, 255, 255, 0.1);");
        scene.setCursor(Cursor.CROSSHAIR);

        redBackgroundStage.setFullScreenExitHint("Press ESC to cancel screenshot mode");
        redBackgroundStage.setFullScreen(true);
        redBackgroundStage.initStyle(StageStyle.TRANSPARENT);
        redBackgroundStage.setAlwaysOnTop(true);
        configureSceneEvents();
        redBackgroundStage.showAndWait();

        return Optional.ofNullable(screenShot);
    }


    private void configureSceneEvents()
    {
        configureMousePressed();
        configureMouseDragged();
        configureMouseReleased();
        configureEscPressed();
    }

    private void configureEscPressed()
    {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, aEvent -> {

            if (aEvent.getCode().equals(KeyCode.ESCAPE))
            {
                redBackgroundStage.close();
                aEvent.consume();
            }

        });
    }

    private void configureMouseReleased()
    {
        scene.addEventHandler(MouseEvent.MOUSE_RELEASED, aEvent -> {

            Optional<Node> aOptRectangle = rootGroup.getChildren().stream()
                    .filter(node -> node instanceof SnippingField)
                    .findFirst();

            if (aOptRectangle.isPresent())
            {
                redBackgroundStage.close();
                rootGroup.getChildren().clear();

                SnippingField drawnRectangle = (SnippingField) aOptRectangle.get();
                WritableImage screenShot = new WritableImage((int) drawnRectangle.getWidth()
                        , (int) drawnRectangle.getHeight());

                double xPosition = drawnRectangle.getX() + drawnRectangle.getTranslateX();
                double yPosition = drawnRectangle.getY() + drawnRectangle.getTranslateY();

                Rectangle region = new Rectangle((int) xPosition, (int) yPosition
                        , (int) drawnRectangle.getWidth(), (int) drawnRectangle.getHeight());

                //Swing is used, because when used Robot from Javafx, screen shot wasn't created
                //on some computers
                try
                {
                    BufferedImage swingScreenShot = new Robot().createScreenCapture(region);
                    this.screenShot = SwingFXUtils.toFXImage(swingScreenShot, screenShot);
                } catch (AWTException e)
                {
                    LOG.error("Cannot create screenshot, an unkown exception has occurred", e);
                }

            }

        });
    }

    private void configureMouseDragged()
    {
        scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, aEvent -> {

            Optional<Node> aOptRectangle = rootGroup.getChildren().stream()
                    .filter(node -> node instanceof SnippingField)
                    .findFirst();

            if (aOptRectangle.isPresent())
            {
                SnippingField drawnRectangle = (SnippingField) aOptRectangle.get();
                drawnRectangle.mouseMoved(aEvent.getX(), aEvent.getY());
            }
        });
    }

    private void configureMousePressed()
    {
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, aEvent -> {
            rootGroup.getChildren().clear();
            SnippingField aSnippingField = new SnippingField(aEvent.getX(), aEvent.getY());
            aSnippingField.setFill(Color.color(RED, GREEN, BLUE, TRANSPARENCY));
            rootGroup.getChildren().add(aSnippingField);
        });
    }


}
