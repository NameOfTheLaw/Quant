package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.DateExtractor;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.TaskEntity;
import ru.ifmo.quant.HandlingState;

/**
 * Created by andrey on 21.11.2016.
 */
@Component
@Scope("prototype")
public class TaskCreatingCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess process) {
        String rawText = input.getText();
        DateExtractor dateExtractor = new DateExtractor(rawText);
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setAccount(process.getAccountEntity());
        taskEntity.setBody(dateExtractor.getText());
        String answer = controlFlow(input, dateExtractor, taskEntity, process);
        return answer;
    }

    protected String controlFlow(QuantMessage input, DateExtractor dateExtractor, TaskEntity taskEntity, HandlingProcess process) {
        String answer;
        if (dateExtractor.isCorrect()) {
            taskEntity.extractDate(dateExtractor);
            taskEntity = dataService.save(taskEntity);
            answer = ctx.getMessage("command.task.create.successful", null, input.getLocale());
            process.changeState(HandlingState.NOTIFICATION_CREATING);
        } else {
            //TODO: fix NullPointerException
            answer = ctx.getMessage("command.task.create.confirmation", new Object[]{taskEntity.getBody()}, input.getLocale());
            process.changeState(HandlingState.TASK_CONFIRMATION);
        }
        process.setParameter("new-task", taskEntity);
        return answer;
    }

}
