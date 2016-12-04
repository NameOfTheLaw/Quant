package ru.ifmo.quant.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

/**
 * Created by andrey on 21.11.2016.
 */
@Component
@Scope("prototype")
public class WeekCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess process) {
        //TODO: realise WeekCommand class logic
        return "WEEK_TEMPLATE";
    }
}
