package ru.ifmo.quant.commands.create;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.DateExtractor;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.HandlingState;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.entities.TaskEntity;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

/**
 * Created by andrey on 10.12.2016.
 */
@Component
@Scope("prototype")
public class CreateTaskCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess handlingProcess) {
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
                    answer = handlingProcess.getHandlingState().getCommandExtractor().extract(input).perform(input, handlingProcess);
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
        return answer;
    }
}
