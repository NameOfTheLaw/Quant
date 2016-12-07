package ru.ifmo.quant.commands.extractors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.QuantMessage;
import ru.ifmo.quant.commands.CommandFactory;
import ru.ifmo.quant.commands.QuantCommand;

/**
 * Default extractor realization using CommandFactory
 *
 * Created by andrey on 04.12.2016.
 */
@Component
public class DefaultExtractor extends CommandExtractor {

    @Autowired
    CommandFactory commandFactory;

    public QuantCommand extract(QuantMessage message) {
        return commandFactory.build(message.getText());
    }
}
