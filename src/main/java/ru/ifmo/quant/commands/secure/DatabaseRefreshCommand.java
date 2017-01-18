package ru.ifmo.quant.commands.secure;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.OutputMessage;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.exceptions.BadCommandReturnException;
import ru.ifmo.quant.exceptions.NullCommandArgumentException;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by andrey on 25.12.2016.
 */
@Component
@Scope("prototype")
public class DatabaseRefreshCommand extends QuantCommand {

    public Queue<QuantMessage> perform(QuantMessage input, HandlingProcess handlingProcess) throws BadCommandReturnException, NullCommandArgumentException {
        Queue<QuantMessage> output = new LinkedList<QuantMessage>();
        dataService.deleteAllNotifications();
        dataService.deleteAllTasks();
        output.add(new OutputMessage(input, ctx.getMessage("admin.intro", null, handlingProcess.getAccountEntity().LOCALE)));
        output.add(new OutputMessage(input, ctx.getMessage("admin.refreshDB", null, handlingProcess.getAccountEntity().LOCALE)));
        return output;
    }
}
