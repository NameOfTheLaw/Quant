package ru.ifmo.quant.commands;

import ru.ifmo.quant.DateExtractor;
import ru.ifmo.quant.HandleState;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.TaskEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

import java.util.Date;

/**
 * Created by andrey on 21.11.2016.
 */
public class TaskCreatingCommand implements QuantCommand {

    public String perform(QuantMessage input, AccountEntity account, HandlingProcess process, DataService dataService) {
        String rawText = input.getText();
        DateExtractor dateExtractor = new DateExtractor(rawText);
        Date date = dateExtractor.getDate();
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setAccount(account);
        taskEntity.setBody(dateExtractor.getText());
        String answer = controlFlow(date, taskEntity, process, dataService);
        return answer;
    }

    protected String controlFlow(Date date, TaskEntity taskEntity, HandlingProcess process, DataService dataService) {
        String answer;
        if (date != null) {
            taskEntity.setServerDate(date);
            taskEntity.setClientDate(date);
            taskEntity = dataService.save(taskEntity);
            answer = "Ok. Do you want me to set notice? Just tell me time or ignore me";
            process.setHandleState(HandleState.CREATING_NOTIFICATION);
        } else {
            answer = "To finish creating task below pls write date \""+taskEntity.getBody()+"...\".\nOr ignore me if something went wrong";
            process.setHandleState(HandleState.CONFIRMATION_TASK);
        }
        process.setParameter("new-task", taskEntity);
        return answer;
    }

}
