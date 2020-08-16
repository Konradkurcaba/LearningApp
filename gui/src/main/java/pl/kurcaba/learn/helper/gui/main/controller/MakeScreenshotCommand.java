package pl.kurcaba.learn.helper.gui.main.controller;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;

public class MakeScreenshotCommand extends AbstractCommand{

    public MakeScreenshotCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        windowController.setMainWindowIconified(true);
//        windowController.makeScreenshot();
        windowController.setMainWindowIconified(false);
    }
}
