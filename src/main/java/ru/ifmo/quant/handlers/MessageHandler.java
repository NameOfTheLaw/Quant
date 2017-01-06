package ru.ifmo.quant.handlers;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
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
import ru.ifmo.quant.security.QuantAuthentificationManager;
import ru.ifmo.quant.security.QuantAuthentificationToken;

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
    private QuantAuthentificationManager authentificationManager;

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HandlingProcess process = processContainer.getProcess(accountEntity);
        if (process == null) {
            process = ctx.getBean("handlingProcess", HandlingProcess.class);
            process.setAccountEntity(accountEntity);
            Authentication response = authentificationManager.authenticate(new QuantAuthentificationToken(accountEntity.getId()));
            process.setAuthentication(response);
            processContainer.addProcess(process);
        }
        QuantCommand command = null;
        try {
            command = process.getHandlingState().extractCommand(input);
        } catch (NoSuchCommandInContextException e) {
            output.add(new OutputMessage(input, ctx.getMessage("error.commandwrongcontext", new Object[] {input.getText()}, input.getLocale())).setKeyboard(KeyboardEnum.CANCEL));
        } catch (NoSuchCommandException e) {
            output.add(new OutputMessage(input, ctx.getMessage("error.nocommand", null, input.getLocale())));
        }
        try {
            output = command.perform(input, process);
        } catch (BadCommandReturnException e) {
            output.add(new OutputMessage(input, ctx.getMessage("error.wtf", null, input.getLocale())));
        } catch (NullCommandArgumentException e) {
            output.add(new OutputMessage(input, ctx.getMessage("error.empty", null, input.getLocale())));
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            output.add(new OutputMessage(input, ctx.getMessage("error.noaccess", null, input.getLocale())));
        }
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

    public QuantAuthentificationManager getAuthentificationManager() {
        return authentificationManager;
    }

    public void setAuthentificationManager(QuantAuthentificationManager authentificationManager) {
        this.authentificationManager = authentificationManager;
    }
}
