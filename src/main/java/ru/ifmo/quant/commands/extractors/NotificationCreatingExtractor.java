package ru.ifmo.quant.commands.extractors;

import org.springframework.stereotype.Component;
import ru.ifmo.quant.commands.NotificationCreatingCommand;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 04.12.2016.
 */
@Component
public class NotificationCreatingExtractor extends CommandExtractor {
    public QuantCommand extract(String string) {
        //TODO: realise method
        QuantCommand command = ctx.getBean("notificationCreatingCommand", QuantCommand.class);
        return command;
    }
}
