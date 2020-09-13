package pl.kurcaba.learn.helper.gui.controlls;

import javafx.scene.control.TableCell;

public class StringTableCell<T> extends TableCell<T,String>
{

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
