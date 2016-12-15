package ru.ifmo.quant;

import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by andrey on 08.11.2016.
 */
public class OutputMessage extends QuantMessage {

    public OutputMessage() {}

    public OutputMessage(QuantMessage input, String text) {
        setLocale(input.getLocale());
        setMessageAddress(input.getMessageAddress());
        setText(text);
    }

}
