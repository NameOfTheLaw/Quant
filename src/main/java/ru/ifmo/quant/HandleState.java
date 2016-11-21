package ru.ifmo.quant;

import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.commands.TaskCreatingCommand;

/**
 * Created by andrey on 08.11.2016.
 */
public enum HandleState {
    CONFIRMATION_NOTIFICATION(null, null),
    CREATING_NOTIFICATION(CONFIRMATION_NOTIFICATION, null),
    CONFIRMATION_TASK(CREATING_NOTIFICATION, null),
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
        return (QuantCommand) commandClass.newInstance();
    }
}
