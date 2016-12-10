package ru.ifmo.quant.commands.create;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.DateExtractor;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.HandlingState;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.entities.NotificationEntity;
import ru.ifmo.quant.entities.TaskEntity;

/**
 * Created by andrey on 10.12.2016.
 */
@Component
@Scope("prototype")
public class CreateNotificationCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess handlingProcess) {
        DateExtractor dateExtractor = new DateExtractor(input.getText());
        String answer;
        if (!isInit()) {
            answer = ctx.getMessage("command.createnotification.intro", null, input.getLocale());
            init();
        } else {
            NotificationEntity notificationEntity = handlingProcess.getParameter(HandlingProcess.NOTIFICATION, NotificationEntity.class);
            if (notificationEntity == null) {
                notificationEntity = new NotificationEntity();
                TaskEntity taskEntity = handlingProcess.getParameter(HandlingProcess.TASK, TaskEntity.class);
                notificationEntity.setTask(taskEntity);
            }
            if (dateExtractor.isCorrect()) {
                notificationEntity.extractDate(dateExtractor);
                dataService.save(notificationEntity);
                answer = ctx.getMessage("command.createnotification.successful", null, input.getLocale());
                handlingProcess.changeState(HandlingState.DEFAULT);
            } else {
                answer = ctx.getMessage("command.createnotification.confirmation", null, input.getLocale());
            }
        }
        return answer;
    }
}
