package ru.ifmo.quant;

import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 08.11.2016.
 */
public class InputMessage extends QuantMessage {

    public InputMessage() {}

    public InputMessage(Update update) {
        String text = update.getMessage().getText().replaceAll("\\s+", " ").trim().toLowerCase();
        setText(text);
        setMessageAddress(new MessageAddress(MessageAddress.TELEGRAM_ALIAS, update.getMessage().getChatId()));
    }

}
