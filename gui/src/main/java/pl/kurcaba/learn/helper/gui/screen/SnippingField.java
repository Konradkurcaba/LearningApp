package pl.kurcaba.learn.helper.gui.screen;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.shape.Rectangle;

public class SnippingField extends Rectangle
{

    private final double drawStartPointX;
    private final double drawStartPointY;

    private final BooleanProperty isDrawingEnd = new SimpleBooleanProperty(false);

    public SnippingField(double drawStartPointX, double drawStartPointY)
    {
        super(drawStartPointX, drawStartPointY, 0, 0);
        this.drawStartPointX = drawStartPointX;
        this.drawStartPointY = drawStartPointY;
    }

    public void mouseMoved(double aNewX, double aNewY)
    {
        updateWidth(aNewX);
        updateHeight(aNewY);
    }

    private void updateWidth(double aNewX)
    {
        double width = aNewX - drawStartPointX;
        if (width < 0)
        {
            setTranslateX(width);
            setWidth(Math.abs(width));
        } else
        {
            setWidth(width);
        }
    }

    private void updateHeight(double aNewY)
    {
        double height = aNewY - drawStartPointY;
        if (height < 0)
        {
            setTranslateY(height);
            setHeight(Math.abs(height));
        } else
        {
            setHeight(height);
        }
    }

}
