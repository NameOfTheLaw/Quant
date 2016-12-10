package ru.ifmo.quant.commands.extractors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingState;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 07.12.2016.
 */
@Component
@Scope("prototype")
public class EditExtractor extends CommandExtractor {

    public QuantCommand extract(QuantMessage message) {
        if (isExecuting()) return getExecutingCommand();
        if (message.getText().equals(ctx.getMessage("command.edittask", null, message.getLocale()))) {
            QuantCommand command = ctx.getBean("chooseTaskCommand", QuantCommand.class);
            setExecutingCommand(command);
            return command;
        }
        if (message.getText().equals(ctx.getMessage("command.editnotification", null, message.getLocale()))) {
            QuantCommand command = ctx.getBean("chooseTaskCommand", QuantCommand.class);
            command.setAfterState(HandlingState.NOTIFICATION_EDIT);
            setExecutingCommand(command);
            return command;
        }
        return null;
    }
}
