package ru.ifmo.quant.commands.edit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.DateExtractor;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.HandlingState;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.entities.TaskEntity;

/**
 * Created by andrey on 07.12.2016.
 */
@Component
@Scope("prototype")
public class TaskReplaceCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess handlingProcess) {
        TaskEntity taskEntity = handlingProcess.getParameter(HandlingProcess.TASK, TaskEntity.class);
        String answer = null;

        if (!isInit()) {
            answer = ctx.getMessage("command.edittask.replacetask.intro", null, input.getLocale());
            init();
        } else {
            DateExtractor dateExtractor = new DateExtractor(input.getText());
            TaskEntity replaceTaskEntity = new TaskEntity();
            replaceTaskEntity.setId(taskEntity.getId());
            replaceTaskEntity.setAccount(handlingProcess.getAccountEntity());
            replaceTaskEntity.setBody(dateExtractor.getText());
            if (dateExtractor.isCorrect()) {
                replaceTaskEntity.extractDate(dateExtractor);
                dataService.save(replaceTaskEntity);
                answer = ctx.getMessage("command.edittask.succesfullend", null, input.getLocale());
                handlingProcess.clearParameters();
                handlingProcess.changeState(HandlingState.DEFAULT);
            } else {
                answer = ctx.getMessage("command.edittask.replacetask", new Object[]{taskEntity.getBody()}, input.getLocale());
            }
        }
        return answer;
    }

}
