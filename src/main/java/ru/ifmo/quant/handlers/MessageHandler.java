package ru.ifmo.quant.handlers;

import ru.ifmo.quant.*;
import ru.ifmo.quant.commands.CommandFactory;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.commands.StartCommand;
import ru.ifmo.quant.commands.TaskCreatingCommand;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.executors.MessagesPool;

/**
 * Created by andrey on 08.11.2016.
 */
public class MessageHandler {

    private ProcessContainer processContainer;
    private DataService dataService;
    private CommandFactory commandFactory;
    private MessagesPool messagesPool;

    public QuantMessage update(QuantMessage input) {
        AccountEntity accountEntity = dataService.findAccountEntity(input);
        if (accountEntity == null) {
            accountEntity = new AccountEntity();
            accountEntity.insertKey(input.getMessageAddress());
            accountEntity = dataService.save(accountEntity);
            QuantCommand command = new StartCommand();
            command.perform(input, null);
        }
        QuantMessage output = new OutputMessage();
        output.setMessageAddress(input.getMessageAddress());
        HandlingProcess process = processContainer.getProcess(accountEntity);
        QuantCommand command = null;
        String answer = "Plz send me text";
        if (input.getText() != null) {
            try {
                command = commandFactory.build(input.getText());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (process == null) {
                process = new HandlingProcess(null, accountEntity);
                processContainer.addProcess(process);
            }
            if (command != null) {
                //Killing active process state
                if (process.getHandleState() != null) process.removeHandleState();
                //Starting process
                try {
                    answer = command.perform(input, process);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    answer = "I don't know how to process this right now";
                    process.removeHandleState();
                } catch (Exception e) {
                    e.printStackTrace();
                    answer = "Some errors happend in my mind. Please repeat maybe i'll be ok";
                }
            } else if (process.getHandleState() != null) {
                //Continue process
                try {
                    command = process.getHandleState().getCommand(commandFactory.getApplicationContext());
                    answer = command.perform(input, process);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    answer = "I don't know how to process this right now";
                    process.removeHandleState();
                } catch (Exception e) {
                    e.printStackTrace();
                    answer = "Some errors happend in my mind. Please repeat maybe i'll be ok";
                }
            } else {
                command = new TaskCreatingCommand();
                try {
                    answer = command.perform(input, process);
                } catch (Exception e) {
                    e.printStackTrace();
                    answer = "Some errors happend in my mind. Please repeat maybe i'll be ok";
                }
            }
        }
        output.setText(answer);
        messagesPool.addToPool(output);
        return output;
    }

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

    public MessagesPool getSendingPool() {
        return messagesPool;
    }

    public void setSendingPool(MessagesPool sendingPool) {
        this.messagesPool = sendingPool;
    }
}
