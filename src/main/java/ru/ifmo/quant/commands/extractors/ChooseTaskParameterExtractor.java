package ru.ifmo.quant.commands.extractors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 10.12.2016.
 */
@Component
@Scope("prototype")
public class ChooseTaskParameterExtractor extends CommandExtractor {

    public QuantCommand extract(QuantMessage message) {
        QuantCommand command = null;
        if (isExecuting()) command = getExecutingCommand();
        if (message.getText().equals(EDIT_BODY_COMMAND)) {
            command = ctx.getBean("taskBodyEditCommand", QuantCommand.class);
            setExecutingCommand(command);
        }
        if (message.getText().equals(EDIT_TIME_COMMAND)) {
            command = ctx.getBean("taskTimeEditCommand", QuantCommand.class);
            setExecutingCommand(command);
        }
        if (message.getText().equals(REPLACE_TASK_COMMAND)) {
            command = ctx.getBean("taskReplaceCommand", QuantCommand.class);
            setExecutingCommand(command);
        }
        if (message.getText().equals(REMOVE_TASK_COMMAND)) {
            command = ctx.getBean("removeTaskCommand", QuantCommand.class);
            setExecutingCommand(command);
        }
        return command;
    }
}
