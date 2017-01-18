package ru.ifmo.quant.commands.extractors;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.commands.QuantCommandNames;

/**
 * Created by andrey on 04.12.2016.
 */
public abstract class CommandExtractor extends QuantCommandNames implements ApplicationContextAware {

    protected ApplicationContext ctx;
    protected QuantCommand executingCommand;

    public abstract QuantCommand extract(QuantMessage message);

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public boolean isExecuting() {
        if (executingCommand == null) {
            return false;
        } else {
            return true;
        }
    }

    public QuantCommand getExecutingCommand() {
        return executingCommand;
    }

    public void setExecutingCommand(QuantCommand command) {
        executingCommand = command;
    }

    public void commandIsOver() {
        executingCommand = null;
    }
}
