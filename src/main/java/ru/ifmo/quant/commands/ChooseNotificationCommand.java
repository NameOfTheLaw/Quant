package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.DateExtractor;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.HandlingState;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entities.NotificationEntity;
import ru.ifmo.quant.entities.TaskEntity;

import java.util.List;

/**
 * Created by andrey on 10.12.2016.
 */
@Component
@Scope("prototype")
public class ChooseNotificationCommand extends QuantCommand {

    private List<NotificationEntity> notificationsList;
    private NotificationEntity notification;

    public String perform(QuantMessage input, HandlingProcess handlingProcess) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!isInit()) {
            notificationsList = dataService.findNotificationEntity(handlingProcess.getParameter(HandlingProcess.TASK, TaskEntity.class));
            if (!notificationsList.isEmpty()) {
                stringBuilder.append(ctx.getMessage("command.editnotification.intro", null, input.getLocale())+"\n");
                int i = 0;
                for (NotificationEntity notificationEntity: notificationsList) {
                    i++;
                    stringBuilder.append(i+") "+notificationEntity.toString()+"\n");
                }
            } else {
                stringBuilder.append(ctx.getMessage("command.editnotification.nonotifications", null, input.getLocale()));
                handlingProcess.clearParameters();
                handlingProcess.changeState(HandlingState.DEFAULT);
            }
            init();
        } else {
            if (notification == null) {
                int i = Integer.parseInt(input.getText()) - 1;
                try {
                    notification = notificationsList.get(i);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                if (notification != null) {
                    stringBuilder.append(ctx.getMessage("command.editnotification.edittime", null, input.getLocale()));
                    handlingProcess.setParameter(HandlingProcess.NOTIFICATION, notification);
                } else {
                    stringBuilder.append(ctx.getMessage("command.editnotification.outofindex", null, input.getLocale()));
                }
            } else {
                DateExtractor dateExtractor = new DateExtractor(input.getText());
                if (dateExtractor.isCorrect()) {
                    notification.extractDate(dateExtractor);
                    dataService.save(notification);
                    stringBuilder.append(ctx.getMessage("command.editnotification.succesfullend", null, input.getLocale()));
                    handlingProcess.clearParameters();
                    handlingProcess.changeState(HandlingState.DEFAULT);
                } else {
                    stringBuilder.append(ctx.getMessage("command.editnotification.edittime.toconfirm", null, input.getLocale()));
                }
            }
        }
        return stringBuilder.toString();
    }
}
