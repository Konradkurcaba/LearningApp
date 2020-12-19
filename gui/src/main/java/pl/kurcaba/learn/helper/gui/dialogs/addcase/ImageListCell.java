package pl.kurcaba.learn.helper.gui.dialogs.addcase;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import pl.kurcaba.learn.helper.gui.controlls.MenuItemCommand;

public class ImageListCell extends ListCell<ImageWithCounterWrapper>
{


    public ImageListCell(ShowImageCommand showImageCommand)
    {
        initContextMenu(showImageCommand);
    }

    private void initContextMenu(ShowImageCommand showImageCommand)
    {
        ContextMenu menu = new ContextMenu();
        MenuItemCommand showImage = new MenuItemCommand("Pokaż obraz");
        showImage.setCommand(showImageCommand);
        menu.getItems().add(showImage);
        setContextMenu(menu);
    }

    @Override
    protected void updateItem(ImageWithCounterWrapper aImage, boolean aEmpty)
    {
        super.updateItem(aImage, aEmpty);
        if(aEmpty || aImage == null)
        {
            setText("");
        }else
        {
            setText("Screenshot " + aImage.getImageNumber());
        }
    }


}
