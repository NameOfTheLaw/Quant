package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.TaskEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

import java.sql.Date;
import java.util.List;

/**
 * Created by andrey on 09.11.2016.
 */
@Component
@Scope("prototype")
public class TodayCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess process) {
        //TODO: realise perform method
        List<TaskEntity> taskEntities = dataService.findTaskEntity(new Date(System.currentTimeMillis()), 1000l * 60 * 60 * 24, process.getAccountEntity());
        StringBuilder stringBuilder = new StringBuilder();

        if (taskEntities.isEmpty()) {
            stringBuilder.append(ctx.getMessage("command.today.empty", null, input.getLocale()));
        } else {
            stringBuilder.append(ctx.getMessage("command.today.intro", null, input.getLocale()));
            for (TaskEntity taskEntity : taskEntities) {
                stringBuilder.append(">" + taskEntity + "\n");
            }
        }
        return stringBuilder.toString();
    }
}
