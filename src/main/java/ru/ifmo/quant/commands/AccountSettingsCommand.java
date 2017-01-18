package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by andrey on 18.01.2017.
 */
@Component
@Scope("prototype")
public class AccountSettingsCommand extends QuantCommand {

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        handlingProcess.changeState(HandlingState.ACCOUNT_SETTINGS);
        output.add(new OutputMessage(input, ctx.getMessage("command.accountsettings.intro", null, handlingProcess.getAccountEntity().LOCALE))
                .setKeyboard(KeyboardEnum.ACCOUNT_SETTINGS));
        return output;
    }
}
