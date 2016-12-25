package ru.ifmo.quant.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.KeyboardEnum;
import ru.ifmo.quant.OutputMessage;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by andrey on 15.12.2016.
 */
@Component
@Scope("prototype")
public class HelpCommand extends QuantCommand {

    @Autowired
    private Properties commandsDescriptions;

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) throws BadCommandReturnException, NullCommandArgumentException {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        output.add(new OutputMessage(input, ctx.getMessage("command.help", null, input.getLocale())));
        StringBuilder stringBuilder = new StringBuilder();
        Enumeration propertyNames = commandsDescriptions.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String name = (String) propertyNames.nextElement();
            stringBuilder.append(name+" - "+commandsDescriptions.get(name)+"\n");
        }
        output.add(new OutputMessage(input, stringBuilder.toString()).setKeyboard(KeyboardEnum.DEFAULT));
        return output;
    }
}
