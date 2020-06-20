package pl.kurcaba.learn.helper.gui;

import javafx.scene.control.TextField;

import java.util.HashMap;


public class TextFieldColorUtil
{

    private static HashMap<Color,String> colorMap = new HashMap();

    static
    {
        colorMap.put(Color.RED, "-fx-text-inner-color: red;");
        colorMap.put(Color.GREEN, "-fx-text-inner-color: green;");
        colorMap.put(Color.BLACK,"-fx-text-inner-color: black;");
    }

    private TextFieldColorUtil()
    {
        //it's util
    }


    public static void colorText(TextField aFieldToColor,Color aColor)
    {
        String colorString = colorMap.get(aColor);
        aFieldToColor.setStyle(colorString);
    }

    public enum Color
    {
        RED,GREEN,BLACK
    }

}