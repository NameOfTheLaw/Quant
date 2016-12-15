package ru.ifmo.quant;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.ifmo.quant.commands.extractors.CommandExtractor;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.exceptions.NoSuchCommandException;
import ru.ifmo.quant.exceptions.NoSuchCommandInContextException;

/**
 * Created by andrey on 04.12.2016.
 */
public class HandlingState implements ApplicationContextAware {

    public static final String DEFAULT = "default";
    public static final String TASK_CREATING = "taskCreating";
    public static final String TASK_CONFIRMATION = "taskConfirmation";
    public static final String NOTIFICATION_CREATING = "notificationCreating";
    public static final String NOTIFICATION_CONFIRMATION = "notificationConfirmation";
    public static final String EDIT = "edit";
    public static final String TASK_EDIT ="taskEdit";
    public static final String CHOOSE_TASK_PARAMETER = "chooseTaskParameter";
    public static final String NOTIFICATION_EDIT = "editNotification";
    public static final String CREATE = "create";
    public static final String NOTIFICATION_CREATE = "createNotification";
    private static final String EXRACTOR_SUFFIX = "Extractor";

    private ApplicationContext ctx;
    private CommandExtractor commandExtractor;

    public QuantCommand extractCommand(QuantMessage message) throws NoSuchCommandException, NoSuchCommandInContextException {
        String text = message.getText();
        QuantCommand quantCommand = commandExtractor.extract(message);
        return quantCommand;
    }

    public CommandExtractor getCommandExtractor() {
        return commandExtractor;
    }

    public void setCommandExtractor(CommandExtractor commandExtractor) {
        this.commandExtractor = commandExtractor;
    }

    public void changeExtractor(String stateName) {
        setCommandExtractor(ctx.getBean(stateName+EXRACTOR_SUFFIX, CommandExtractor.class));
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
