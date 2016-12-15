package ru.ifmo.quant.handlers;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.ifmo.quant.*;
import ru.ifmo.quant.commands.CommandFactory;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.commands.StartCommand;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entities.AccountEntity;
import ru.ifmo.quant.MessagesPool;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NoSuchCommandException;
import ru.ifmo.quant.exceptions.NoSuchCommandInContextException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by andrey on 08.11.2016.
 */
public class MessageHandler implements ApplicationContextAware {

    private ApplicationContext ctx;
    private ProcessContainer processContainer;
    private DataService dataService;
    private CommandFactory commandFactory;
    private MessagesPool messagesPool;

    public Queue<QuantMessage> update(QuantMessage input) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        AccountEntity accountEntity = dataService.findAccountEntity(input);
        if (accountEntity == null) {
            accountEntity = new AccountEntity();
            accountEntity.insertKey(input.getMessageAddress());
            accountEntity = dataService.save(accountEntity);
            QuantCommand command = ctx.getBean("startCommand", QuantCommand.class);
            try {
                command.perform(input, null);
            } catch (BadCommandReturnException e) {
                e.printStackTrace();
            } catch (NullCommandArgumentException e) {
                e.printStackTrace();
            }
        }
        HandlingProcess process = processContainer.getProcess(accountEntity);
        if (process == null) {
            process = ctx.getBean("handlingProcess", HandlingProcess.class);
            process.setAccountEntity(accountEntity);
            processContainer.addProcess(process);
        }
        String answer = null;
        QuantCommand command = null;
        try {
            command = process.getHandlingState().extractCommand(input);
        } catch (NoSuchCommandInContextException e) {
            e.printStackTrace();
            answer = ctx.getMessage("error.commandwrongcontext", new Object[] {input.getText()}, input.getLocale());
            output.add(new OutputMessage(input, answer).setKeyboard(KeyboardEnum.CANCEL_KEYBOARD));
            return output;
        } catch (NoSuchCommandException e) {
            e.printStackTrace();
            answer = ctx.getMessage("error.nocommand", null, input.getLocale());
        }
        try {
            output = command.perform(input, process);
            messagesPool.addToPool(output);
            return output;
        } catch (BadCommandReturnException e) {
            e.printStackTrace();
            answer = ctx.getMessage("error.wtf", null, input.getLocale());
        } catch (NullCommandArgumentException e) {
            e.printStackTrace();
            answer = ctx.getMessage("error.empty", null, input.getLocale());
        } catch (NullPointerException e) {
            //it's ok
        }
        output.add(new OutputMessage(input, answer));
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

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
