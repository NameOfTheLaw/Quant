package ru.ifmo.quant.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantLocaleService;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

import java.util.Queue;

/**
 * Created by andrey on 09.11.2016.
 */
public abstract class QuantCommand extends QuantCommandNames implements ApplicationContextAware {

    protected ApplicationContext ctx;
    protected boolean init = false;
    protected String afterState;

    @Autowired
    protected DataService dataService;
    @Autowired
    protected QuantLocaleService quantLocaleService;

    public abstract Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) throws BadCommandReturnException;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    protected boolean isInit() {
        return init;
    }

    protected void init() {
        init = true;
    }

    public String getAfterState() {
        return afterState;
    }

    public void setAfterState(String afterState) {
        this.afterState = afterState;
    }

    public boolean isAfterState() {
        if (getAfterState() != null) return true;
        return false;
    }

}
