package pl.kurcaba.learn.helper.gui.controller.main;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.dialogs.confirm.ConfirmationStatus;

public abstract class MainWindowCommand implements CommandIf{

    protected MainWindowController windowController;
    protected GuiModelBroker guiModelBroker;

    public MainWindowCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        this.guiModelBroker = aGuiModelBroker;
        this.windowController = aWindowController;
    }

    protected void revertFocus() {
        windowController.getDisplayedLearnSet().ifPresent(windowController::changeFocus);
    }

    protected boolean canChangeLearnSet()
    {
        boolean hasUnsavedChanges = guiModelBroker.getUnsavedChangesProperty().get();
        if (hasUnsavedChanges) {
            ConfirmationStatus status = displayWarningWindow();
            boolean userCanceledChange = status.equals(ConfirmationStatus.REJECTED);
            return !userCanceledChange;
        }
        return true;
    }

    private ConfirmationStatus displayWarningWindow() {
        ConfirmationStatus status = windowController
                .displayConfirmDialog("Posiadasz niezapisane zmiany," +
                        " czy chcesz kontynuwać ? Twoje zmiany nie zostaną zapisane.");
        return status;
    }

}
