package ru.ifmo.quant.commands.create;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.entities.NotificationEntity;
import ru.ifmo.quant.entities.TaskEntity;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by andrey on 10.12.2016.
 */
@Component
@Scope("prototype")
public class CreateNotificationCommand extends QuantCommand {

    @Autowired
    DateTimeService dateTimeService;

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        ExtractedDate extractedDate = dateTimeService.extractDate(input, handlingProcess.getAccountEntity());
        String answer;
        if (!isInit()) {
            output.add(new OutputMessage(input, ctx.getMessage("command.createnotification.intro", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())))
                .setKeyboard(KeyboardEnum.CANCEL));
            init();
            return output;
        } else {
            NotificationEntity notificationEntity = handlingProcess.getParameter(HandlingProcess.NOTIFICATION, NotificationEntity.class);
            if (notificationEntity == null) {
                notificationEntity = new NotificationEntity();
                TaskEntity taskEntity = handlingProcess.getParameter(HandlingProcess.TASK, TaskEntity.class);
                notificationEntity.setTask(taskEntity);
            }
            if (extractedDate.isCorrect()) {
                notificationEntity.loadDate(extractedDate);
                dataService.save(notificationEntity);
                answer = ctx.getMessage("command.createnotification.successful", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity()));
                handlingProcess.clearParameters();
                handlingProcess.changeState(HandlingState.DEFAULT);
                output.add(new OutputMessage(input, answer).setKeyboard(KeyboardEnum.DEFAULT));
                return output;
            } else {
                answer = ctx.getMessage("command.createnotification.confirmation", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity()));
            }
        }
        output.add(new OutputMessage(input, answer));
        return output;
    }
}
