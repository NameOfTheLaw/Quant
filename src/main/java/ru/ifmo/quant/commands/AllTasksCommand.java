package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.KeyboardEnum;
import ru.ifmo.quant.OutputMessage;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entities.TaskEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by andrey on 09.11.2016.
 */
@Component
@Scope("prototype")
public class AllTasksCommand extends QuantCommand {

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        List<TaskEntity> tasks = dataService.findTaskEntity(handlingProcess.getAccountEntity());
        StringBuilder stringBuilder = new StringBuilder();
        if (!tasks.isEmpty()) {
            output.add(new OutputMessage(input, ctx.getMessage("command.alltasks.intro", null, handlingProcess.getAccountEntity().LOCALE)).setKeyboard(KeyboardEnum.DEFAULT));
            for (TaskEntity task : tasks) {
                stringBuilder.append("> " + task.toString() + "\n");
            }
            output.add(new OutputMessage(input, stringBuilder.toString()));
        } else {
            output.add(new OutputMessage(input, ctx.getMessage("command.alltasks.empty", null, handlingProcess.getAccountEntity().LOCALE)).setKeyboard(KeyboardEnum.DEFAULT));
        }
        return output;
    }
}
