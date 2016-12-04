package ru.ifmo.quant.commands;

import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

/**
 * Created by andrey on 21.11.2016.
 */
public class WeekCommand extends QuantCommand {

    public String perform(QuantMessage input, HandlingProcess process) {
        //TODO: realise WeekCommand class logic
        return "WEEK_TEMPLATE";
    }
}
