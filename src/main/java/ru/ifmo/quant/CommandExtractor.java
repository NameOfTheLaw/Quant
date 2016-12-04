package ru.ifmo.quant;

import ru.ifmo.quant.commands.QuantCommand;

/**
 * Created by andrey on 04.12.2016.
 */
public abstract class CommandExtractor {

    public abstract QuantCommand extract(String string);

}
