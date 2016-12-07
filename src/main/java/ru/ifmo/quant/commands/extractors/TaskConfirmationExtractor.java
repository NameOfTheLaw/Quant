package ru.ifmo.quant.commands.extractors;

import org.springframework.stereotype.Component;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 04.12.2016.
 */
@Component
public class TaskConfirmationExtractor extends CommandExtractor {

    public QuantCommand extract(QuantMessage message) {
        //TODO: realise method
        QuantCommand command = ctx.getBean("taskConfirmationCommand", QuantCommand.class);
        return command;
    }
}
