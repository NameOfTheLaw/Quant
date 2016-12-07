package ru.ifmo.quant.commands.extractors;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 04.12.2016.
 */
public abstract class CommandExtractor implements ApplicationContextAware {

    protected ApplicationContext ctx;

    public abstract QuantCommand extract(QuantMessage message);

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
