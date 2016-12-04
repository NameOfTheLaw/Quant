package ru.ifmo.quant.states;

import ru.ifmo.quant.CommandExtractor;
import ru.ifmo.quant.HandlingProcess;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.QuantCommand;
import ru.ifmo.quant.entity.AccountEntity;

import java.util.Map;

/**
 * Created by andrey on 04.12.2016.
 */
public abstract class HandlingState {

    CommandExtractor commandExtractor;
    Map<String, Object> stateParameters;

    public QuantCommand extractCommand(QuantMessage message) {
        String text = message.getText();
        QuantCommand quantCommand = commandExtractor.extract(text);
        return quantCommand;
    }

    public CommandExtractor getCommandExtractor() {
        return commandExtractor;
    }

    public void setCommandExtractor(CommandExtractor commandExtractor) {
        this.commandExtractor = commandExtractor;
    }

}
