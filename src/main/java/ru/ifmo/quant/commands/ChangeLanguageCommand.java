package ru.ifmo.quant.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
import ru.ifmo.quant.exceptions.BadCommandReturnException;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by andrey on 18.01.2017.
 */
@Component
@Scope("prototype")
public class ChangeLanguageCommand extends QuantCommand {

    @Autowired
    QuantLocaleService quantLocaleService;

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        if (!isInit()) {
            output.add(new OutputMessage(input, ctx.getMessage("command.accountsettings.changelanguage.intro", null, handlingProcess.getAccountEntity().LOCALE))
            .setKeyboard(KeyboardEnum.CHANGE_LANGUAGE));
            init();
        } else {
            String language = input.getText().replace("/","");
            if (quantLocaleService.isLocale(language)) {
                handlingProcess.getAccountEntity().setLanguage(language);
                dataService.save(handlingProcess.getAccountEntity());
                output.add(new OutputMessage(input, ctx.getMessage("command.accountsettings.changelanguage.successfull", null, handlingProcess.getAccountEntity().LOCALE))
                    .setKeyboard(KeyboardEnum.DEFAULT));
                if (isAfterState()) {
                    handlingProcess.changeState(getAfterState());
                    try {
                        output.addAll(handlingProcess.getHandlingState().getCommandExtractor().extract(input).perform(input, handlingProcess));
                    } catch (BadCommandReturnException e) {
                        e.printStackTrace();
                    }
                } else {
                    handlingProcess.changeState(HandlingState.DEFAULT);
                }
            } else {
                output.add(new OutputMessage(input, ctx.getMessage("command.accountsettings.changelanguage.wronglanguage", null, handlingProcess.getAccountEntity().LOCALE))
                    .setKeyboard(KeyboardEnum.CHANGE_LANGUAGE));
            }
        }
        return output;
    }
}
