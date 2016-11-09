package ru.ifmo.quant.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.OutputMessage;
import ru.ifmo.quant.ProcessContainer;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.CommandFactory;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.dao.QuantDao;
import ru.ifmo.quant.dao.account.AccountDao;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

/**
 * Created by andrey on 08.11.2016.
 */
public class MessageHandler {

    ProcessContainer processContainer;
    QuantDao quantDao;
    CommandFactory commandFactory;

    public QuantMessage update(QuantMessage input) {
        AccountEntity accountEntity = quantDao.getAccountDao().findByQuantMessage(input);
        QuantMessage output = new OutputMessage();
        output.setMessageAddress(input.getMessageAddress());
        HandlingProcess process = processContainer.getProcess(accountEntity);
        //TODO: optimise the message handle process
        QuantCommand command = commandFactory.build(input.getText());
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
            //Creating task or notification (process controls creation)
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

    public QuantDao getQuantDao() {
        return quantDao;
    }

    public void setQuantDao(QuantDao quantDao) {
        this.quantDao = quantDao;
    }

    public CommandFactory getCommandFactory() {
        return commandFactory;
    }

    public void setCommandFactory(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }
}
