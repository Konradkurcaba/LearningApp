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
        super(drawStartPointX,drawStartPointY,0,0);
        this.drawStartPointX = drawStartPointX;
        this.drawStartPointY = drawStartPointY;

    }

    public void mouseMoved(double aNewX,double aNewY)
    {
        setWidth(aNewX - drawStartPointX);
        setHeight(aNewY - drawStartPointY);
    }


    public BooleanProperty isDrawingEndProperty()
    {
        return isDrawingEnd;
    }
}
