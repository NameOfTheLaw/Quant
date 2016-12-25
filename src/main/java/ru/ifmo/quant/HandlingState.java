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
    public static final String EDIT = "edit";
    public static final String CHOOSE_TASK_PARAMETER = "chooseTaskParameter";
    public static final String NOTIFICATION_EDIT = "editNotification";
    public static final String CREATE = "create";
    public static final String NOTIFICATION_CREATE = "createNotification";
    private static final String EXRACTOR_SUFFIX = "Extractor";
    private String currentExtractorName;

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
        String extractorName = stateName+EXRACTOR_SUFFIX;
        setCommandExtractor(ctx.getBean(extractorName, CommandExtractor.class));
        setCurrentExtractorName(extractorName);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public String getCurrentExtractorName() {
        return currentExtractorName;
    }

    public void setCurrentExtractorName(String currentExtractorName) {
        this.currentExtractorName = currentExtractorName;
    }
}
