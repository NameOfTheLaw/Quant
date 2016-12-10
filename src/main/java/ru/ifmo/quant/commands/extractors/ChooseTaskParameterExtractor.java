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
        if (message.getText().equals(ctx.getMessage("command.edittask.editbody", null, message.getLocale()))) {
            command = ctx.getBean("taskBodyEditCommand", QuantCommand.class);
            setExecutingCommand(command);
        }
        if (message.getText().equals(ctx.getMessage("command.edittask.edittime", null, message.getLocale()))) {
            command = ctx.getBean("taskTimeEditCommand", QuantCommand.class);
            setExecutingCommand(command);
        }
        if (message.getText().equals(ctx.getMessage("command.edittask.replacetask", null, message.getLocale()))) {
            command = ctx.getBean("taskReplaceCommand", QuantCommand.class);
            setExecutingCommand(command);
        }
        return command;
    }
}
