package ru.ifmo.quant.commands.extractors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 18.01.2017.
 */
@Component
@Scope("prototype")
public class AccountSettingsExtractor extends CommandExtractor {

    public QuantCommand extract(QuantMessage message) {
        if (isExecuting()) return getExecutingCommand();
        QuantCommand command = null;
        switch (message.getText()) {
            case CHANGE_LANGUAGE_COMMAND:
                command = ctx.getBean("changeLanguageCommand", QuantCommand.class);
                break;
            case CHANGE_TIME_ZONE_COMMAND:
                command = ctx.getBean("changeLocationCommand", QuantCommand.class);
        }
        setExecutingCommand(command);
        return command;
    }
}
