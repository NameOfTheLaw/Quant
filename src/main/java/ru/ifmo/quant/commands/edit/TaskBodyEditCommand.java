package ru.ifmo.quant.commands.edit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.entities.TaskEntity;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by andrey on 07.12.2016.
 */
@Component
@Scope("prototype")
public class TaskBodyEditCommand extends QuantCommand {

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        TaskEntity taskEntity = handlingProcess.getParameter(HandlingProcess.TASK, TaskEntity.class);
        String answer = null;
        if (!isInit()) {
            ctx.getMessage("command.edittask.editbody.intro", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity()));
            init();
        } else {
            taskEntity.setBody(input.getText());
            dataService.save(taskEntity);
            answer = ctx.getMessage("command.edittask.succesfullend", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity()));
            handlingProcess.clearParameters();
            handlingProcess.changeState(HandlingState.DEFAULT);
            output.add(new OutputMessage(input, answer)
                    .setKeyboard(KeyboardEnum.DEFAULT));
            return output;
        }
        output.add(new OutputMessage(input, answer));
        return output;
    }
}
