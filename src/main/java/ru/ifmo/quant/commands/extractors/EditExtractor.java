package ru.ifmo.quant.commands.extractors;

import org.springframework.stereotype.Component;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 07.12.2016.
 */
@Component
public class EditExtractor extends CommandExtractor {

    public QuantCommand extract(QuantMessage message) {
        String text = message.getText();
        if (text.equals(ctx.getMessage("command.edittask", null, message.getLocale()))) {
            return ctx.getBean("preTaskEditCommand", QuantCommand.class);
        }
        if (text.equals(ctx.getMessage("command.editnotification", null, message.getLocale()))) {
            return ctx.getBean("preNotificationEditCommand", QuantCommand.class);
        }
        return null;
    }
}
