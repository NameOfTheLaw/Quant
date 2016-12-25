package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by andrey on 22.11.2016.
 */
@Component
@Scope("prototype")
public class CancelCommand extends QuantCommand {

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        handlingProcess.clearParameters();
        handlingProcess.changeState(HandlingState.DEFAULT);
        String answer = ctx.getMessage("command.cancel.text", null, input.getLocale());
        output.add(new OutputMessage(input, answer).setKeyboard(KeyboardEnum.DEFAULT));
        return output;
    }
}
