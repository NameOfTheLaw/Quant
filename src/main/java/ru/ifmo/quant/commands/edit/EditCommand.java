package ru.ifmo.quant.commands.edit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
import ru.ifmo.quant.commands.QuantCommand;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by andrey on 07.12.2016.
 */
@Component
@Scope("prototype")
public class EditCommand extends QuantCommand {

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        handlingProcess.clearParameters();
        handlingProcess.changeState(HandlingState.EDIT);
        String answer = ctx.getMessage("command.edit.intro", null, input.getLocale());
        output.add(new OutputMessage(input, answer).setKeyboard(KeyboardEnum.EDIT_KEYBOARD));
        return output;
    }
}
