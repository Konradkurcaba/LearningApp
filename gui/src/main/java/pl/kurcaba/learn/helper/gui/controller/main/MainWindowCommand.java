package pl.kurcaba.learn.helper.gui.controller.main;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;

public abstract class MainWindowCommand implements CommandIf{

    protected MainWindowController windowController;
    protected GuiModelBroker guiModelBroker;

    public MainWindowCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        this.guiModelBroker = aGuiModelBroker;
        this.windowController = aWindowController;
    }

}
