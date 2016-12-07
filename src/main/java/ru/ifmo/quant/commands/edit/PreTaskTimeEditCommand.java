package ru.ifmo.quant.commands.edit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.HandlingState;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 07.12.2016.
 */
@Component
@Scope("prototype")
public class PreTaskTimeEditCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess handlingProcess) {
        String answer = ctx.getMessage("command.edittask.edittime.intro", null, input.getLocale());
        handlingProcess.changeState(HandlingState.TASK_TIME_EDIT);
        return answer;
    }
}
