package ru.ifmo.quant;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.ifmo.quant.commands.extractors.CommandExtractor;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 04.12.2016.
 */
public class HandlingState implements ApplicationContextAware {

    public static final String DEFAULT="defaultExtractor";
    public static final String TASK_CREATING="taskCreatingExtractor";
    public static final String TASK_CONFIRMATION="taskConfirmationExtractor";
    public static final String NOTIFICATION_CREATING="notificationCreatingExtractor";
    public static final String NOTIFICATION_CONFIRMATION="notificationConfirmationExtractor";

    private ApplicationContext ctx;
    private CommandExtractor commandExtractor;

    public QuantCommand extractCommand(QuantMessage message) {
        String text = message.getText();
        QuantCommand quantCommand = commandExtractor.extract(text);
        if (quantCommand == null) {
            changeExtractor(DEFAULT);
            quantCommand = commandExtractor.extract(text);
        }
        return quantCommand;
    }

    public CommandExtractor getCommandExtractor() {
        return commandExtractor;
    }

    public void setCommandExtractor(CommandExtractor commandExtractor) {
        this.commandExtractor = commandExtractor;
    }

    public void changeExtractor(String stateName) {
        setCommandExtractor(ctx.getBean(stateName, CommandExtractor.class));
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
