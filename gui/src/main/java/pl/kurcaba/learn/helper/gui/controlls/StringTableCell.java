package pl.kurcaba.learn.helper.gui.controlls;

import javafx.geometry.Insets;
import javafx.scene.control.TableCell;

public class StringTableCell<T> extends TableCell<T,String>
{

    public StringTableCell()
    {
        setPadding(new Insets(0,0,0,15));
    }

    @Override
    protected void updateItem(String item, boolean empty)
    {
        if (item != null && !empty)
        {
            setText(item);
        } else
        {
            setText("");
        }
    }
}
