package ru.ifmo.quant.commands.extractors;

import org.springframework.stereotype.Component;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.commands.TaskConfirmationCommand;

/**
 * Created by andrey on 04.12.2016.
 */
@Component
public class TaskConfirmationExtractor extends CommandExtractor {
    public QuantCommand extract(String string) {
        //TODO: realise method
        QuantCommand command = ctx.getBean("taskConfirmationCommand", QuantCommand.class);
        return command;
    }
}
