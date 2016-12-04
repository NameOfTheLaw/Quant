package ru.ifmo.quant.commands;

import ru.ifmo.quant.HandleState;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

/**
 * Created by andrey on 22.11.2016.
 */
public class PreTaskCreatingCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess process){
        process.setHandleState(HandleState.CREATING_TASK);
        String answer = ctx.getMessage("command.task.create", null, input.getLocale());
        return answer;
    }
}
