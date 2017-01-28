package ru.ifmo.quant.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
import ru.ifmo.quant.entities.AccountEntity;
import ru.ifmo.quant.exceptions.BadCommandReturnException;

import java.util.*;

/**
 * Created by andrey on 09.11.2016.
 */
@Component
@Scope("prototype")
public class StartCommand extends QuantCommand {

    @Autowired
    DateTimeService dateTimeService;

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        if (handlingProcess.getAccountEntity() == null) {
            output.add(new OutputMessage(input, ctx.getMessage("command.start", null, quantLocaleService.getDefault())));
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.insertKey(input.getMessageAddress());
            accountEntity.setTimeZone(dateTimeService.getDefaultTimeZone().getID());
            accountEntity.setLanguage(quantLocaleService.getDefault().getLanguage());
            dataService.save(accountEntity);
            handlingProcess.setAccountEntity(accountEntity);
            handlingProcess.changeState(HandlingState.CHOOSE_LANGUAGE_ON_START);
            try {
                output.addAll(handlingProcess.getHandlingState().getCommandExtractor().extract(input).perform(input, handlingProcess));
            } catch (BadCommandReturnException e) {
                e.printStackTrace();
            }
        } else {
            output.add(new OutputMessage(input, ctx.getMessage("command.start.again", null, quantLocaleService.getLocale(handlingProcess.getAccountEntity())))
                .setKeyboard(KeyboardEnum.DEFAULT));
        }
        return output;
    }

}
