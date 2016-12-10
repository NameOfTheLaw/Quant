package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.HandlingState;

/**
 * Created by andrey on 22.11.2016.
 */
@Component
@Scope("prototype")
public class CancelCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess handlingProcess) {
        handlingProcess.clearParameters();
        handlingProcess.changeState(HandlingState.DEFAULT);
        String answer = ctx.getMessage("command.cancel.text", null, input.getLocale());
        return answer;
    }
}
