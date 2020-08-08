package pl.kurcaba.learn.helper.gui.controller;

import pl.kurcaba.learn.helper.gui.backend.GuiModelBroker;

public class AddCaseCommand extends AbstractCommand {


    public AddCaseCommand(GuiModelBroker aGuiModelBroker, MainWindowController aWindowController) {
        super(aGuiModelBroker, aWindowController);
    }

    @Override
    public void executeCommand() {
        String newCaseName = windowController.getNameFieldText();
        String newDefinition = windowController.getDefinitionFieldText();
        guiModelBroker.createNewCase(newCaseName, newDefinition);
//        if (lastScreenshot != null)
//        {
//            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
//            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(lastScreenshot, null);
//            try
//            {
//                ImageIO.write(bufferedImage, "png", byteStream);
//            } catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//            view.setImage(byteStream.toByteArray());
//        }
//        caseViews.add(view);
//        refreshTableData();
//        name.clear();
//        definition.clear();
    }
}
