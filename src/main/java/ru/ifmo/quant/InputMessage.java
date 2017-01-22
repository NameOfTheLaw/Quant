package ru.ifmo.quant;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 08.11.2016.
 */
public class InputMessage extends QuantMessage {

    public InputMessage() {}

    public InputMessage(Update update) {
        Message message = update.getMessage();
        setDate(message.getDate());
        if (message.hasText()) {
            setText(message.getText().replaceAll("\\s+", " ").trim().toLowerCase());
        }
        if (message.getLocation()!=null) {
            setLocation(message.getLocation());
        }
        setMessageAddress(new MessageAddress(MessageAddress.TELEGRAM_ALIAS, message.getChatId()));
    }

}
