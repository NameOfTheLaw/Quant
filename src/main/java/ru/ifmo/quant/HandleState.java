package ru.ifmo.quant;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.ifmo.quant.commands.*;

/**
 * Created by andrey on 08.11.2016.
 */
public enum HandleState {
    //TODO: REMOVE ENUMERATIONS AND ADD STATE CLASSES
    CONFIRMATION_NOTIFICATION("notificationConfirmationCommand"),
    CREATING_NOTIFICATION("notificationCreatingCommand"),
    CONFIRMATION_TASK("taskConfirmationCommand"),
    CREATING_TASK("taskCreatingCommand");

    private String commandAlias;

    HandleState(String commandAlias) {
        this.commandAlias = commandAlias;
    }

    public QuantCommand getCommand(ApplicationContext ctx) {
        if (commandAlias!=null) {
            //TODO: CTX IS NULL HERE
            return ctx.getBean(commandAlias, QuantCommand.class);
        } else {
            throw new NullPointerException();
        }
    }

}
