package ru.ifmo.quant.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
import ru.ifmo.quant.entities.NotificationEntity;
import ru.ifmo.quant.entities.TaskEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by andrey on 10.12.2016.
 */
@Component
@Scope("prototype")
public class ChooseNotificationCommand extends QuantCommand {

    private List<NotificationEntity> notificationsList;
    private NotificationEntity notification;

    @Autowired
    DateTimeService dateTimeService;
    @Autowired
    QuantFormatter quantFormatter;

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        StringBuilder stringBuilder = new StringBuilder();
        if (!isInit()) {
            notificationsList = dataService.findNotificationEntity(handlingProcess.getParameter(HandlingProcess.TASK, TaskEntity.class));
            if (!notificationsList.isEmpty()) {
                stringBuilder.append(ctx.getMessage("command.editnotification.intro", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())))
                    .append("\n");
                for (NotificationEntity notificationEntity: notificationsList) {
                    stringBuilder.append(notificationsList.indexOf(notificationEntity))
                            .append(") ")
                            .append(quantFormatter.format(notificationEntity))
                            .append("\n");
                }
            } else {
                stringBuilder.append(ctx.getMessage("command.editnotification.nonotifications", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())));
                handlingProcess.clearParameters();
                handlingProcess.changeState(HandlingState.DEFAULT);
            }
            init();
        } else {
            if (notification == null) {
                try {
                    int i = Integer.parseInt(input.getText()) - 1;
                    notification = notificationsList.get(i);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                if (notification != null) {
                    stringBuilder.append(ctx.getMessage("command.editnotification.edittime", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())));
                    handlingProcess.setParameter(HandlingProcess.NOTIFICATION, notification);
                } else {
                    stringBuilder.append(ctx.getMessage("command.editnotification.outofindex", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())));
                }
            } else {
                ExtractedDate extractedDate = dateTimeService.extractDate(input, handlingProcess.getAccountEntity());
                if (extractedDate.isCorrect()) {
                    notification.loadDate(extractedDate);
                    dataService.save(notification);
                    stringBuilder.append(ctx.getMessage("command.editnotification.succesfullend", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())));
                    handlingProcess.clearParameters();
                    handlingProcess.changeState(HandlingState.DEFAULT);
                    output.add(new OutputMessage(input, stringBuilder.toString())
                            .setKeyboard(KeyboardEnum.DEFAULT));
                    return output;
                } else {
                    stringBuilder.append(ctx.getMessage("command.editnotification.edittime.toconfirm", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())));
                }
            }
        }
        output.add(new OutputMessage(input, stringBuilder.toString()));
        return output;
    }
}
