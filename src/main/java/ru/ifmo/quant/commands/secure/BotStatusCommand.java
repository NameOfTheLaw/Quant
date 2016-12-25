package ru.ifmo.quant.commands.secure;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.KeyboardEnum;
import ru.ifmo.quant.OutputMessage;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by andrey on 16.12.2016.
 */
@Component
@Scope("prototype")
public class BotStatusCommand extends QuantCommand {
    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) throws BadCommandReturnException, NullCommandArgumentException {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        output.add(new OutputMessage(input, ctx.getMessage("admin.intro", null, input.getLocale())));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ctx.getMessage("admin.botstatus.count.accounts", null, input.getLocale()) + dataService.countAccountEntities() + "\n");
        stringBuilder.append(ctx.getMessage("admin.botstatus.count.tasks", null, input.getLocale()) + dataService.countTaskEntities() + "\n");
        stringBuilder.append(ctx.getMessage("admin.botstatus.count.notifications", null, input.getLocale()) + dataService.countNotificationEntities() + "\n");
        stringBuilder.append(ctx.getMessage("admin.botstatus.servertime", null, input.getLocale()) + new Date(System.currentTimeMillis()));
        output.add(new OutputMessage(input, stringBuilder.toString()).setKeyboard(KeyboardEnum.ADMIN));
        return output;
    }
}
