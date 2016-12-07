package ru.ifmo.quant.commands.edit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.DateExtractor;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.HandlingState;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.entity.TaskEntity;

/**
 * Created by andrey on 07.12.2016.
 */
@Component
@Scope("prototype")
public class TaskTimeEditCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess handlingProcess) {
        TaskEntity taskEntity = handlingProcess.getParameter(HandlingProcess.TASK, TaskEntity.class);
        String answer;
        DateExtractor dateExtractor = new DateExtractor(input.getText());
        if (input.getText() != null) {
            if (dateExtractor.isCorrect()) {
                taskEntity.extractDate(dateExtractor);
                dataService.save(taskEntity);
                answer = ctx.getMessage("command.edittask.succesfullend", null, input.getLocale());
                handlingProcess.clearParameters();
                handlingProcess.changeState(HandlingState.DEFAULT);
            } else {
                answer = ctx.getMessage("command.edittask.edittime.toconfirm", null, input.getLocale());
            }
        } else {
            throw new NullPointerException();
        }
        return answer;
    }
}
