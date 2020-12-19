package pl.kurcaba.learn.helper.gui.controller.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.dialogs.addcase.NewCaseDto;
import pl.kurcaba.learn.helper.gui.dialogs.editcase.EditCaseWindowDisplayer;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.util.Optional;

public class EditCaseCommand extends MainWindowCommand
{

    private static final Logger logger = LogManager.getLogger(EditCaseCommand.class);

    public EditCaseCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController)
    {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand()
    {
        Optional<LearnCaseView> view = getWindowController().getSelectedCaseView();
        view.ifPresentOrElse(this::editCase
                ,() -> logger.error("Cannot edit a learn case, any case isn't selected"));
    }

    @Override
    public boolean canBeExecuted()
    {
        return getWindowController().getSelectedCaseView().isPresent();
    }

    private void editCase(LearnCaseView aCaseToEdit)
    {
        EditCaseWindowDisplayer editDisplayer = new EditCaseWindowDisplayer();
        NewCaseDto editedDto = editDisplayer.showEditCaseWindow(aCaseToEdit);
        guiModelBroker.editCase(aCaseToEdit.getId(), editedDto);
        getWindowController().refreshSetData();
    }
}
