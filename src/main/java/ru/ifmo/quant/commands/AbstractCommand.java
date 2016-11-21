package ru.ifmo.quant.commands;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.quant.dao.DataService;

/**
 * Created by andrey on 09.11.2016.
 */
public abstract class AbstractCommand implements QuantCommand {

    @Autowired
    DataService dataService;

}
