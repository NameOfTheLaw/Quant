package ru.ifmo.quant.commands;

import ru.ifmo.quant.DateExtractor;
import ru.ifmo.quant.HandleState;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.NotificationEntity;
import ru.ifmo.quant.entity.TaskEntity;

import java.util.Date;

/**
 * Created by andrey on 25.11.2016.
 */
public class NotificationCreatingCommand implements QuantCommand {
    public String perform(QuantMessage input, AccountEntity account, HandlingProcess process, DataService dataService) {
        String rawText = input.getText();
        DateExtractor dateExtractor = new DateExtractor(rawText);
        NotificationEntity notificationEntity = new NotificationEntity();
        TaskEntity taskEntity = process.getParameter("new-task", TaskEntity.class);
        notificationEntity.setTask(taskEntity);
        String answer = controlFlow(dateExtractor.getDate(), notificationEntity, process, dataService);
        return answer;
    }

    protected String controlFlow(Date date, NotificationEntity notificationEntity, HandlingProcess process, DataService dataService) {
        String answer;
        if (date != null) {
            notificationEntity.setServerDate(date);
            notificationEntity.setClientDate(date);
            dataService.save(notificationEntity);
            answer = "Ok. I will notice you!";
            process.removeHandleState();
        } else {
            answer = "Pls choose date for the notification";
            process.setHandleState(HandleState.CONFIRMATION_NOTIFICATION);
        }
        return answer;
    }

}
