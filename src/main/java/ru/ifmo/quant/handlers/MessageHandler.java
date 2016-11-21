package ru.ifmo.quant.handlers;

import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.OutputMessage;
import ru.ifmo.quant.ProcessContainer;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.CommandFactory;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

/**
 * Created by andrey on 08.11.2016.
 */
public class MessageHandler {

    private ProcessContainer processContainer;
    private DataService dataService;
    private CommandFactory commandFactory;

    public QuantMessage update(QuantMessage input) {
        AccountEntity accountEntity = dataService.findAccountEntity(input);
        QuantMessage output = new OutputMessage();
        output.setMessageAddress(input.getMessageAddress());
        HandlingProcess process = processContainer.getProcess(accountEntity);
        //TODO: optimise the message handle process
        QuantCommand command = commandFactory.build(input.getText());
        if (process == null) {

        } else {

        }
        if (command != null) {
            //One of commands
            String commandResult;
            try {
                commandResult = command.perform(input, accountEntity, process);
            } catch (WrongContextCommandException e) {
                e.printStackTrace();
                commandResult = "Hmm, i don't understand that;( \n Please tell me what do you want";
            }
            output.setText(commandResult);
        } else {
            //Creating task or notification (creation process)
            output.setText("I don't know that command!");
        }
        return output;
    };

    public ProcessContainer getProcessContainer() {
        return processContainer;
    }

    public void setProcessContainer(ProcessContainer processContainer) {
        this.processContainer = processContainer;
    }

    public CommandFactory getCommandFactory() {
        return commandFactory;
    }

    public void setCommandFactory(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public DataService getDataService() {
        return dataService;
    }

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }
}
