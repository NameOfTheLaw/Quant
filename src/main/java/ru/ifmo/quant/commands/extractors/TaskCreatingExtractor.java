package ru.ifmo.quant.commands.extractors;

import org.springframework.stereotype.Component;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.commands.TaskCreatingCommand;

/**
 * Created by andrey on 04.12.2016.
 */
@Component
public class TaskCreatingExtractor extends CommandExtractor {
    public QuantCommand extract(String string) {
        //TODO: realise method
        QuantCommand command = ctx.getBean("taskCreatingCommand", QuantCommand.class);
        return command;
    }
}
