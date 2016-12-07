package ru.ifmo.quant.commands.edit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.HandlingState;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.List;

/**
 * Created by andrey on 07.12.2016.
 */
@Component
@Scope("prototype")
public class TaskEditCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess handlingProcess) {
        List<TaskEntity> tasks = handlingProcess.getParameter(HandlingProcess.TASKS_LIST, List.class);
        int i = Integer.parseInt(input.getText())-1;
        TaskEntity task = null;
        try {
            task = tasks.get(i);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        String text;
        if (task != null) {
            text = ctx.getMessage("command.edit.task.change", null, input.getLocale());
            handlingProcess.clearParameters();
            handlingProcess.setParameter(HandlingProcess.TASK, task);
            handlingProcess.changeState(HandlingState.TASK_PARAMETERS_EDIT);
        } else {
            text = ctx.getMessage("command.edit.task.outoftasksindex", null, input.getLocale());
        }
        stringBuilder.append(text+"\n");
        return stringBuilder.toString();
    }
}
