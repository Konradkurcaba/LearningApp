package pl.kurcaba.learn.helper.gui.main.controller;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;
import pl.kurcaba.learn.helper.gui.view.LearnCaseView;

import java.util.Optional;

public class ShowImageCommand extends AbstractCommand
{
    public ShowImageCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        Optional<LearnCaseView> viewCase = windowController.getSelectedCaseView();
        if(viewCase.isPresent())
        {

        }
    }
}
