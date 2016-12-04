package ru.ifmo.quant.commands;

import ru.ifmo.quant.DateExtractor;
import ru.ifmo.quant.HandleState;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.Locale;

/**
 * Created by andrey on 21.11.2016.
 */
public class TaskCreatingCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess process) {
        String rawText = input.getText();
        DateExtractor dateExtractor = new DateExtractor(rawText);
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setAccount(process.getAccountEntity());
        taskEntity.setBody(dateExtractor.getText());
        String answer = controlFlow(input, dateExtractor, taskEntity, process, dataService);
        return answer;
    }

    protected String controlFlow(QuantMessage input, DateExtractor dateExtractor, TaskEntity taskEntity, HandlingProcess process, DataService dataService) {
        String answer;
        if (dateExtractor.isCorrect()) {
            taskEntity.extractDate(dateExtractor);
            taskEntity = dataService.save(taskEntity);
            answer = ctx.getMessage("command.task.create.successful", null, input.getLocale());
            process.setHandleState(HandleState.CREATING_NOTIFICATION);
        } else {
            answer = ctx.getMessage("command.task.create.confirmation", new Object[]{taskEntity.getBody()}, input.getLocale());
            process.setHandleState(HandleState.CONFIRMATION_TASK);
        }
        process.setParameter("new-task", taskEntity);
        return answer;
    }

}
