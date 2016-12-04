package ru.ifmo.quant.commands;

import ru.ifmo.quant.DateExtractor;
import ru.ifmo.quant.HandleState;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.entity.NotificationEntity;
import ru.ifmo.quant.entity.TaskEntity;

/**
 * Created by andrey on 25.11.2016.
 */
public class NotificationCreatingCommand extends QuantCommand {
    public String perform(QuantMessage input, HandlingProcess process) {
        String rawText = input.getText();
        DateExtractor dateExtractor = new DateExtractor(rawText);
        NotificationEntity notificationEntity = new NotificationEntity();
        TaskEntity taskEntity = process.getParameter("new-task", TaskEntity.class);
        notificationEntity.setTask(taskEntity);
        String answer = controlFlow(input, dateExtractor, notificationEntity, process, dataService);
        return answer;
    }

    protected String controlFlow(QuantMessage input, DateExtractor dateExtractor, NotificationEntity notificationEntity, HandlingProcess process, DataService dataService) {
        String answer;
        if (dateExtractor.isCorrect()) {
            notificationEntity.extractDate(dateExtractor);
            dataService.save(notificationEntity);
            answer = ctx.getMessage("command.notification.create.successful", null, input.getLocale());
            process.removeHandleState();
        } else {
            answer = ctx.getMessage("command.notification.create.confirmation", null, input.getLocale());
            process.setHandleState(HandleState.CONFIRMATION_NOTIFICATION);
        }
        return answer;
    }

}
