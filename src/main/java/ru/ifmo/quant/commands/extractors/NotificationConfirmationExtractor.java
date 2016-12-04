package ru.ifmo.quant.commands.extractors;

import org.springframework.stereotype.Component;
import ru.ifmo.quant.commands.NotificationConfirmationCommand;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 04.12.2016.
 */
@Component
public class NotificationConfirmationExtractor extends CommandExtractor {
    public QuantCommand extract(String string) {
        //TODO: realise method
        QuantCommand command = ctx.getBean("notificationConfirmationCommand", QuantCommand.class);
        return command;
    }
}
