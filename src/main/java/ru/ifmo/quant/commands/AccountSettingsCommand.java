package ru.ifmo.quant.commands;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    QuantFormatter quantFormatter;

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        handlingProcess.changeState(HandlingState.ACCOUNT_SETTINGS);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ctx.getMessage("command.accountsettings.intro", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())))
                .append(quantFormatter.format(handlingProcess.getAccountEntity()))
                .append(ctx.getMessage("command.accountsettings.options", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())));
        output.add(new OutputMessage(input, stringBuilder.toString())
                .setKeyboard(KeyboardEnum.ACCOUNT_SETTINGS));
        return output;
    }
}
