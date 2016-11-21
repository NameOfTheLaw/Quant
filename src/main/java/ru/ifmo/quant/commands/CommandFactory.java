package ru.ifmo.quant.commands;

import org.springframework.beans.factory.InitializingBean;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * Created by andrey on 09.11.2016.
 */
public class CommandFactory implements InitializingBean {

    private Properties commands;
    private Map<String,Class> commandMap;

    public void afterPropertiesSet() throws Exception {
        Enumeration enumeration = commands.propertyNames();
        while (enumeration.hasMoreElements()) {
            String alias = (String) enumeration.nextElement();
            try {
                Class classObject = Class.forName(commands.getProperty(alias));
                commandMap.put(alias, classObject);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        commands.clear();
    }

    //TODO: realise "Strategy" pattern
    public QuantCommand build(String body) {
        String alias = body.replaceAll("\\s+", " ").trim();
        QuantCommand command = null;
        try {
            command = (QuantCommand) commandMap.get(alias).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return command;
    }

    public Properties getCommands() {
        return commands;
    }

    public void setCommands(Properties commands) {
        this.commands = commands;
    }
}
