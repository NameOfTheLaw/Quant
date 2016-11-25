package ru.ifmo.quant.commands;

import org.springframework.beans.factory.InitializingBean;

import java.util.Enumeration;
import java.util.HashMap;
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
        commandMap = new HashMap<String, Class>();
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
        if (body != null) {
            String alias = body.replaceAll("\\s+", " ").trim().toLowerCase();
            QuantCommand command = null;
            try {
                command = (QuantCommand) commandMap.get(alias).newInstance();
            } catch (NullPointerException e) {
                //ignore. it's ok. all goes as planned
            } catch (Exception e) {
                e.printStackTrace();
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
}
