package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entities.TaskEntity;

import java.util.List;

/**
 * Created by andrey on 09.11.2016.
 */
@Component
@Scope("prototype")
public class AllTasksCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess handlingProcess) {
        List<TaskEntity> tasks = dataService.findTaskEntity(handlingProcess.getAccountEntity());
        StringBuilder stringBuilder = new StringBuilder();
        for (TaskEntity task: tasks) {
            stringBuilder.append("> "+task.toString()+"\n");
        }
        return stringBuilder.toString();
    }
}
