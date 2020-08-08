package pl.kurcaba.learn.helper.gui.controller;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;

public abstract class AbstractCommand {


    protected MainWindowController windowController;
    protected GuiModelBroker guiModelBroker;

    public AbstractCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {

        this.guiModelBroker = aGuiModelBroker;
        this.windowController = aWindowController;
    }

    public abstract void executeCommand();

}
