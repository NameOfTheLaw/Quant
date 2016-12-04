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
public class PreTaskCreatingCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess process){
        process.changeState(HandlingState.TASK_CREATING);
        String answer = ctx.getMessage("command.task.create", null, input.getLocale());
        return answer;
    }
}
