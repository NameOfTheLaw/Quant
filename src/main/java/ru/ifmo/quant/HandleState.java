package ru.ifmo.quant;

import ru.ifmo.quant.commands.*;

/**
 * Created by andrey on 08.11.2016.
 */
public enum HandleState {
    CONFIRMATION_NOTIFICATION(null, NotificationConfirmationCommand.class),
    CREATING_NOTIFICATION(CONFIRMATION_NOTIFICATION, NotificationCreatingCommand.class),
    CONFIRMATION_TASK(CREATING_NOTIFICATION, TaskConfirmationCommand.class),
    CREATING_TASK(CONFIRMATION_TASK, TaskCreatingCommand.class);

    private HandleState nextState;
    private Class commandClass;

    HandleState(HandleState nextState, Class commandClass) {
        this.nextState = nextState;
        this.commandClass = commandClass;
    }

    public HandleState getNextState() {
        return nextState;
    }

    public void setNextState(HandleState nextState) {
        this.nextState = nextState;
    }

    public QuantCommand getCommand() throws IllegalAccessException, InstantiationException {
        if (commandClass!=null) {
            return (QuantCommand) commandClass.newInstance();
        } else {
            throw new NullPointerException();
        }
    }
}
