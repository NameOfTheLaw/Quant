package ru.ifmo.quant.commands;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by andrey on 09.11.2016.
 */
public class CommandFactory implements InitializingBean, ApplicationContextAware {

    private Properties commands;
    private Map<String,String> commandMap;
    private ApplicationContext ctx;

    public void afterPropertiesSet() throws Exception {
        Enumeration enumeration = commands.propertyNames();
        commandMap = new HashMap<String, String>();
        while (enumeration.hasMoreElements()) {
            String alias = (String) enumeration.nextElement();
            commandMap.put(alias, commands.getProperty(alias));
        }
        commands.clear();
    }

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

    public Properties getCommands() {
        return commands;
    }

    public void setCommands(Properties commands) {
        this.commands = commands;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return ctx;
    }
}
