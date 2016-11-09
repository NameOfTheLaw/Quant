package ru.ifmo.quant;

import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 08.11.2016.
 */
public class InputMessage extends QuantMessage {

    public InputMessage(Update update) {
        setText(update.getMessage().getText());
        setMessageAddress(new MessageAddress(MessageAddress.TELEGRAM_ALIAS, update.getMessage().getChatId()));
    }

}
