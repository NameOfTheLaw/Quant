package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entities.TaskEntity;

import java.sql.Date;
import java.util.List;

/**
 * Created by andrey on 21.11.2016.
 */
@Component
@Scope("prototype")
public class WeekCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess process) {
        List<TaskEntity> taskEntities = dataService.findTaskEntityForWeek(new Date(System.currentTimeMillis()), process.getAccountEntity());
        StringBuilder stringBuilder = new StringBuilder();

        if (taskEntities.isEmpty()) {
            stringBuilder.append(ctx.getMessage("command.week.empty", null, input.getLocale()));
        } else {
            stringBuilder.append(ctx.getMessage("command.week.intro", null, input.getLocale()));
            for (TaskEntity taskEntity : taskEntities) {
                stringBuilder.append(">" + taskEntity + "\n");
            }
        }
        return stringBuilder.toString();
    }
}
