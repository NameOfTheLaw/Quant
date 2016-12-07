package ru.ifmo.quant.commands.edit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.HandlingState;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.entity.TaskEntity;

/**
 * Created by andrey on 07.12.2016.
 */
@Component
@Scope("prototype")
public class TaskBodyEditCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess handlingProcess) {
        TaskEntity taskEntity = handlingProcess.getParameter(HandlingProcess.TASK, TaskEntity.class);
        String answer;
        if (input.getText() != null) {
            taskEntity.setBody(input.getText());
            dataService.save(taskEntity);
            answer = ctx.getMessage("command.edittask.succesfullend", null, input.getLocale());
            handlingProcess.clearParameters();
            handlingProcess.changeState(HandlingState.DEFAULT);
        } else {
            throw new NullPointerException();
        }
        return answer;
    }
}
