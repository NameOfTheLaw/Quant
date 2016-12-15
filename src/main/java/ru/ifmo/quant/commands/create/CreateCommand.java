package ru.ifmo.quant.commands.create;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
import ru.ifmo.quant.commands.QuantCommand;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by andrey on 10.12.2016.
 */
@Component
@Scope("prototype")
public class CreateCommand extends QuantCommand {

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        handlingProcess.clearParameters();
        handlingProcess.changeState(HandlingState.CREATE);
        output.add(new OutputMessage(input, ctx.getMessage("command.create.intro", null, input.getLocale())).setKeyboard(KeyboardEnum.CREATE_KEYBOARD));
        return output;
    }
}
