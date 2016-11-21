package ru.ifmo.quant.commands;

import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.TaskEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

import java.util.List;

/**
 * Created by andrey on 09.11.2016.
 */
public class AllTasksCommand extends AbstractCommand {

    public String perform(QuantMessage input, AccountEntity account, HandlingProcess process) throws WrongContextCommandException {
        //TODO: realise perform method
        List<TaskEntity> tasks = dataService.findTaskEntity(account);
        StringBuilder stringBuilder = new StringBuilder();
        for (TaskEntity task: tasks) {
            stringBuilder.append("> "+task.toString());
        }
        return stringBuilder.toString();
    }
}