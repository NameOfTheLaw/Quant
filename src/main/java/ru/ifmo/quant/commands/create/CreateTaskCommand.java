package ru.ifmo.quant.commands.create;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.entities.TaskEntity;
import ru.ifmo.quant.exceptions.BadCommandReturnException;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by andrey on 10.12.2016.
 */
@Component
@Scope("prototype")
public class CreateTaskCommand extends QuantCommand {

    @Autowired
    DateTimeService dateTimeService;

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        String answer;

        if (!isInit()) {
            output.add(new OutputMessage(input, ctx.getMessage("command.createtask.intro", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())))
                    .setKeyboard(KeyboardEnum.CANCEL));
            if (input.getText().equals(CT_COMMAND)) {
                handlingProcess.clearParameters();
                handlingProcess.changeState(HandlingState.CREATE);
                handlingProcess.getHandlingState().getCommandExtractor().setExecutingCommand(this);
            }
            init();
            return output;
        } else {
            TaskEntity taskEntity = handlingProcess.getParameter(HandlingProcess.TASK, TaskEntity.class);
            ExtractedDate extractedDate = dateTimeService.extractDate(input, handlingProcess.getAccountEntity());
            if (taskEntity == null) {
                taskEntity = new TaskEntity();
                taskEntity.setBody(extractedDate.getText());
                taskEntity.setAccount(handlingProcess.getAccountEntity());
            }
            if (extractedDate.isCorrect()) {
                taskEntity.loadDate(extractedDate);
                if (taskEntity.getBody() == null) {
                    taskEntity.setBody(ctx.getMessage("template.emptytaskbody", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())));
                }
                taskEntity = dataService.save(taskEntity);
                answer = ctx.getMessage("command.createtask.successful", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity()));
                handlingProcess.setParameter(HandlingProcess.TASK, taskEntity);
                if (isAfterState()) {
                    handlingProcess.changeState(getAfterState());
                } else {
                    handlingProcess.changeState(HandlingState.NOTIFICATION_CREATE);
                }
                try {
                    output.add(new OutputMessage(input, answer));
                    output.addAll(handlingProcess.getHandlingState().getCommandExtractor().extract(input).perform(input, handlingProcess));
                    return output;
                } catch (BadCommandReturnException e) {
                    e.printStackTrace();
                }
            } else {
                answer = ctx.getMessage("command.createtask.confirmation", new Object[]{taskEntity.getBody()}, quantLocaleService.getLocale(handlingProcess.getAccountEntity()));
            }
            handlingProcess.setParameter(HandlingProcess.TASK, taskEntity);
        }
        output.add(new OutputMessage(input, answer));
        return output;
    }
}
