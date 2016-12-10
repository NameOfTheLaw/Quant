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
public class EditNotificationExtractor extends CommandExtractor {

    public QuantCommand extract(QuantMessage message) {

        if (isExecuting()) return getExecutingCommand();
        QuantCommand command = ctx.getBean("chooseNotificationCommand", QuantCommand.class);
        setExecutingCommand(command);

        return command;
    }
}
