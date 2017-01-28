package ru.ifmo.quant.commands.edit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.entities.TaskEntity;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by andrey on 16.12.2016.
 */
@Component
@Scope("prototype")
public class RemoveTaskCommand extends QuantCommand {

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        dataService.delete(handlingProcess.getParameter(HandlingProcess.TASK, TaskEntity.class));
        output.add(new OutputMessage(input, ctx.getMessage("command.edittask.removetask.succesfull", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())))
            .setKeyboard(KeyboardEnum.DEFAULT));
        handlingProcess.clearParameters();
        handlingProcess.changeState(HandlingState.DEFAULT);
        return output;
    }
}
