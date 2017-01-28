package ru.ifmo.quant.handlers;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
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
@Component
public class MessageHandler implements ApplicationContextAware {

    private ApplicationContext ctx;
    @Autowired
    private ProcessContainer processContainer;
    @Autowired
    private DataService dataService;
    @Autowired
    private QuantLocaleService quantLocaleService;
    @Autowired
    private MessagesPool messagesPool;
    @Autowired
    private QuantAuthentificationManager authentificationManager;

    public Queue<QuantMessage> update(QuantMessage input) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        AccountEntity account = dataService.findAccountEntity(input);
        HandlingProcess process;
        if (account == null) {
            process = ctx.getBean("handlingProcess", HandlingProcess.class);
            QuantCommand command = ctx.getBean("startCommand", QuantCommand.class);
            try {
                output.addAll(command.perform(input, process));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Authentication response = authentificationManager.authenticate(new QuantAuthentificationToken(process.getAccountEntity().getId()));
            process.setAuthentication(response);
            processContainer.addProcess(process);
        } else {
            process = processContainer.getProcess(account);
            if (process == null) {
                process = ctx.getBean("handlingProcess", HandlingProcess.class);
                process.setAccountEntity(account);
                Authentication response = authentificationManager.authenticate(new QuantAuthentificationToken(account.getId()));
                process.setAuthentication(response);
                processContainer.addProcess(process);
            }
            QuantCommand command = null;
            try {
                command = process.getHandlingState().extractCommand(input);
            } catch (NoSuchCommandInContextException e) {
                output.add(new OutputMessage(input, ctx.getMessage("error.commandwrongcontext", new Object[] {input.getText()}, quantLocaleService.getLocale(account)))
                        .setKeyboard(KeyboardEnum.CANCEL));
            } catch (NoSuchCommandException e) {
                output.add(new OutputMessage(input, ctx.getMessage("error.nocommand", null, quantLocaleService.getLocale(account))));
            }
            try {
                output = command.perform(input, process);
            } catch (BadCommandReturnException e) {
                output.add(new OutputMessage(input, ctx.getMessage("error.wtf", null, quantLocaleService.getLocale(account))));
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                output.add(new OutputMessage(input, ctx.getMessage("error.noaccess", null, quantLocaleService.getLocale(account))));
            }
        }
        messagesPool.addToPool(output);
        return output;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

}
