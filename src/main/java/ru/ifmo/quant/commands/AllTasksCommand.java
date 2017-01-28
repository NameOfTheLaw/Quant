package ru.ifmo.quant.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
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

    @Autowired
    QuantFormatter quantFormatter;

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        List<TaskEntity> tasks = dataService.findTaskEntity(handlingProcess.getAccountEntity());
        StringBuilder stringBuilder = new StringBuilder();
        if (!tasks.isEmpty()) {
            stringBuilder.append(ctx.getMessage("command.alltasks.intro", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())));
            for (TaskEntity task : tasks) {
                stringBuilder.append(">> ")
                    .append(quantFormatter.format(task))
                    .append("\n");
            }
            output.add(new OutputMessage(input, stringBuilder.toString()));
        } else {
            output.add(new OutputMessage(input, ctx.getMessage("command.alltasks.empty", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity()))).setKeyboard(KeyboardEnum.DEFAULT));
        }
        return output;
    }
}
