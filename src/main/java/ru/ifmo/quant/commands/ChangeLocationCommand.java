package ru.ifmo.quant.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.*;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.TimeZone;

/**
 * Created by andrey on 22.01.2017.
 */
@Component
@Scope("prototype")
public class ChangeLocationCommand extends QuantCommand {

    @Autowired
    private TimeZoneService timeZoneService;

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        if (!isInit()) {
            output.add(new OutputMessage(input, ctx.getMessage("command.changelocation.atstart", null, handlingProcess.getAccountEntity().LOCALE))
                .setKeyboard(KeyboardEnum.SEND_LOCATION));
            init();
        } else {
            if (input.getLocation()!=null) {
                TimeZone clientTimeZone = timeZoneService.extract(input);
                if (clientTimeZone != null) {
                    handlingProcess.getAccountEntity().setTimeZone(clientTimeZone.getID());
                    dataService.save(handlingProcess.getAccountEntity());
                    output.add(new OutputMessage(input, ctx.getMessage("command.changelocation.success", new Object[] {clientTimeZone.getID()}, handlingProcess.getAccountEntity().LOCALE))
                            .setKeyboard(KeyboardEnum.DEFAULT));
                    handlingProcess.changeState(HandlingState.DEFAULT);
                } else {
                    output.add(new OutputMessage(input, ctx.getMessage("command.changelocation.badgeoservice", null, handlingProcess.getAccountEntity().LOCALE))
                        .setKeyboard(KeyboardEnum.DEFAULT));
                    handlingProcess.changeState(HandlingState.DEFAULT);
                }
            } else {
                output.add(new OutputMessage(input, ctx.getMessage("command.changelocation.atstart", null, handlingProcess.getAccountEntity().LOCALE))
                    .setKeyboard(KeyboardEnum.SEND_LOCATION));
            }
        }
        return output;
    }
}
