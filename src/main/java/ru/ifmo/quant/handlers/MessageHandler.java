package ru.ifmo.quant.handlers;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.ifmo.quant.*;
import ru.ifmo.quant.commands.CommandFactory;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.commands.StartCommand;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.MessagesPool;

/**
 * Created by andrey on 08.11.2016.
 */
public class MessageHandler implements ApplicationContextAware {

    private ApplicationContext ctx;
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
        if (process == null) {
            process = ctx.getBean("handlingProcess", HandlingProcess.class);
            process.setAccountEntity(accountEntity);
            processContainer.addProcess(process);
        }
        QuantCommand command = process.getHandlingState().extractCommand(input);
        String answer = command.perform(input, process);
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

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
