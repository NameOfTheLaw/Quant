package ru.ifmo.quant.commands.create;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.HandlingState;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 10.12.2016.
 */
@Component
@Scope("prototype")
public class CreateCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess handlingProcess) {
        handlingProcess.clearParameters();
        handlingProcess.changeState(HandlingState.CREATE);
        return ctx.getMessage("command.create.intro", null, input.getLocale());
    }
}
