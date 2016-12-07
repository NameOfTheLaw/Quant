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
public class PreTaskEditCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess handlingProcess) {
        List<TaskEntity> tasks = dataService.findTaskEntity(handlingProcess.getAccountEntity());
        StringBuilder stringBuilder = new StringBuilder();
        if (!tasks.isEmpty()) {
            handlingProcess.setParameter(HandlingProcess.TASKS_LIST, tasks);
            String text = ctx.getMessage("command.edit.task.intro", null, input.getLocale());
            stringBuilder.append(text+"\n");
            int i = 0;
            for (TaskEntity task: tasks) {
                i++;
                stringBuilder.append(i+") "+task.toString()+"\n");
            }
            handlingProcess.changeState(HandlingState.TASK_EDIT);
        } else {
            String text = ctx.getMessage("command.edit.task.notasks", null, input.getLocale());
            stringBuilder.append(text);
            handlingProcess.changeState(HandlingState.DEFAULT);
        }
        return stringBuilder.toString();
    }
}
