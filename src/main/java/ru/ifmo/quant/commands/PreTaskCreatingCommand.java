package ru.ifmo.quant.commands;

import ru.ifmo.quant.HandleState;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

/**
 * Created by andrey on 22.11.2016.
 */
public class PreTaskCreatingCommand extends AbstractCommand {
    public String perform(QuantMessage input, AccountEntity account, HandlingProcess process) throws WrongContextCommandException {
        process.setHandleState(HandleState.CREATING_TASK);
        String answer = "Ok, just write your task and time below";
        return answer;
    }
}
