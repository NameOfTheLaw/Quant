package ru.ifmo.quant.commands.extractors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingState;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 22.01.2017.
 */
@Component
@Scope("prototype")
public class ChooseLanguageOnStartExtractor extends CommandExtractor {

    public QuantCommand extract(QuantMessage message) {
        if (isExecuting()) return getExecutingCommand();
        QuantCommand command = ctx.getBean("changeLanguageCommand", QuantCommand.class);
        command.setAfterState(HandlingState.CHOOSE_LOCATION_ON_START);
        setExecutingCommand(command);
        return command;
    }
}
