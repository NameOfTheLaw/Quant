package ru.ifmo.quant.commands.extractors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingState;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 10.12.2016.
 */
@Component
@Scope("prototype")
public class CreateExtractor extends CommandExtractor {

    public QuantCommand extract(QuantMessage message) {
        if (isExecuting()) return getExecutingCommand();

        QuantCommand command;
        if (message.getText().equals(CREATE_TASK_COMMAND)) {
            command = ctx.getBean("createTaskCommand", QuantCommand.class);
            setExecutingCommand(command);
            return command;
        }
        if (message.getText().equals(CREATE_NOTIFICATION_COMMAND)) {
            command = ctx.getBean("chooseTaskCommand", QuantCommand.class);
            command.setAfterState(HandlingState.NOTIFICATION_CREATE);
            setExecutingCommand(command);
            return command;
        }

        return null;
    }
}
