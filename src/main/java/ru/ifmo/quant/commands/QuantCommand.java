package ru.ifmo.quant.commands;

import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.dao.DataService;
import ru.ifmo.quant.entity.AccountEntity;
import ru.ifmo.quant.exceptions.WrongContextCommandException;

/**
 * Created by andrey on 09.11.2016.
 */
public interface QuantCommand {

    String perform(QuantMessage input, AccountEntity account, HandlingProcess process, DataService dataService) throws WrongContextCommandException;
}
