package ru.ifmo.quant.commands;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * CommandFactory realisation based on properties file
 *
 * Created by andrey on 09.11.2016.
 */
@Component
public class CommandFactory implements ApplicationContextAware {

    private ApplicationContext ctx;
    @Value("#{defaultCommands}")
    private Map<String,String> commandMap;

    public QuantCommand build(String body) {
        if (body != null) {
            String alias = body.replaceAll("\\s+", " ").trim().toLowerCase();
            String beanName = commandMap.get(alias);
            QuantCommand command = null;
            if (beanName != null) {
                try {
                    command = ctx.getBean(commandMap.get(alias), QuantCommand.class);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            return command;
        } else {
            throw new NullPointerException();
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return ctx;
    }
}
