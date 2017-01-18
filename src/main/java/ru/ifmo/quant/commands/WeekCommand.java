package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.OutputMessage;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entities.TaskEntity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by andrey on 21.11.2016.
 */
@Component
@Scope("prototype")
public class WeekCommand extends QuantCommand {

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess process) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        List<TaskEntity> taskEntities = dataService.findTaskEntityForWeek(process.getAccountEntity(), new Timestamp(System.currentTimeMillis()));
        StringBuilder stringBuilder = new StringBuilder();

        if (taskEntities.isEmpty()) {
            stringBuilder.append(ctx.getMessage("command.week.empty", null, input.getLocale()));
        } else {
            stringBuilder.append(ctx.getMessage("command.week.intro", null, input.getLocale()));
            for (TaskEntity taskEntity : taskEntities) {
                stringBuilder.append(">" + taskEntity + "\n");
            }
        }
        output.add(new OutputMessage(input, stringBuilder.toString()));
        return output;
    }
}
