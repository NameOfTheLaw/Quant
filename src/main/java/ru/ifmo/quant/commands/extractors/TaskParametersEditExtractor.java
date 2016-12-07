package ru.ifmo.quant.commands.extractors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;

import java.util.HashMap;

/**
 * Created by andrey on 07.12.2016.
 */
@Component
public class TaskParametersEditExtractor extends CommandExtractor {

    public QuantCommand extract(QuantMessage message) {
        String text = message.getText();
        if (text.equals(ctx.getMessage("command.edittask.editbody", null, message.getLocale()))) {
            return ctx.getBean("preTaskBodyEditCommand", QuantCommand.class);
        }
        if (text.equals(ctx.getMessage("command.edittask.edittime", null, message.getLocale()))) {
            return ctx.getBean("preTaskTimeEditCommand", QuantCommand.class);
        }
        if (text.equals(ctx.getMessage("command.edittask.replacetask", null, message.getLocale()))) {
            return ctx.getBean("preTaskReplaceCommand", QuantCommand.class);
        }
        System.out.println("1");
        return null;
    }

}
