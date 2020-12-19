package pl.kurcaba.learn.helper.gui.controller.main;

import pl.kurcaba.learn.helper.gui.backend.AbstractCommand;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.core.ApplicationConstants;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

public abstract class MainWindowCommand extends AbstractCommand<MainWindowController>
{

    protected GuiModelBroker guiModelBroker;

    public MainWindowCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController)
    {
        super(aWindowController);
        this.guiModelBroker = aGuiModelBroker;
    }

    protected void revertFocus()
    {
        getWindowController().getDisplayedLearnSet().ifPresent(getWindowController()::changeFocus);
    }

    protected boolean canChangeLearnSet()
    {
        boolean hasUnsavedChanges = guiModelBroker.getUnsavedChangesProperty().get();
        if (hasUnsavedChanges)
        {
            ConfirmationStatus status = displayWarningWindow();
            boolean userCanceledChange = status.equals(ConfirmationStatus.REJECTED);
            return !userCanceledChange;
        }
        return true;
    }

    private ConfirmationStatus displayWarningWindow()
    {
        ResourceBundle textBundle = ListResourceBundle.getBundle(ApplicationConstants.DEFAULT_BUNDLE_NAME);
        return getWindowController().displayConfirmDialog(textBundle.getString("unsavedChangesWarning"));
    }

}
