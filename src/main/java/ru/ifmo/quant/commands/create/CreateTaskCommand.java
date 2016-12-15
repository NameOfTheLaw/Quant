package ru.ifmo.quant.commands.create;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.entities.TaskEntity;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by andrey on 10.12.2016.
 */
@Component
@Scope("prototype")
public class CreateTaskCommand extends QuantCommand {

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        String answer;

        if (!isInit()) {
            answer = ctx.getMessage("command.createtask.intro", null, input.getLocale());
            init();
        } else {
            TaskEntity taskEntity = handlingProcess.getParameter(HandlingProcess.TASK, TaskEntity.class);
            DateExtractor dateExtractor = new DateExtractor(input.getText());
            if (taskEntity == null) {
                taskEntity = new TaskEntity();
                taskEntity.setBody(dateExtractor.getText());
                taskEntity.setAccount(handlingProcess.getAccountEntity());
            }
            if (dateExtractor.isCorrect()) {
                taskEntity.extractDate(dateExtractor);
                taskEntity = dataService.save(taskEntity);
                answer = ctx.getMessage("command.createtask.successful", null, input.getLocale());
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
                } catch (NullCommandArgumentException e) {
                    e.printStackTrace();
                }
            } else {
                answer = ctx.getMessage("command.createtask.confirmation", new Object[]{taskEntity.getBody()}, input.getLocale());
            }
            handlingProcess.setParameter(HandlingProcess.TASK, taskEntity);
        }
        output.add(new OutputMessage(input, answer));
        return output;
    }
}
