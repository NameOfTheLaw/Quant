package ru.ifmo.quant.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

/**
 * Created by andrey on 09.11.2016.
 */
public abstract class QuantCommand implements ApplicationContextAware {

    protected ApplicationContext ctx;

    @Autowired
    protected DataService dataService;

    public abstract String perform(QuantMessage input, HandlingProcess handlingProcess);

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }
}
