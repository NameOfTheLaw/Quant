package ru.ifmo.quant.commands.extractors;

import org.springframework.stereotype.Component;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 07.12.2016.
 */
@Component
public class TaskReplaceExtractor extends CommandExtractor {

    public QuantCommand extract(QuantMessage message) {
        return ctx.getBean("taskReplaceCommand", QuantCommand.class);
    }
}
