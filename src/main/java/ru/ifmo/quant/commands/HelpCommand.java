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

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        output.add(new OutputMessage(input, ctx.getMessage("command.help", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity()))));
        output.add(new OutputMessage(input, ctx.getMessage("command.help.howto", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())))
            .setKeyboard(KeyboardEnum.DEFAULT));
        return output;
    }
}
