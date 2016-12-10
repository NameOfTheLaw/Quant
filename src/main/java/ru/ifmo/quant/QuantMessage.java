package ru.ifmo.quant;

import java.util.List;
import java.util.Locale;

/**
 * Created by andrey on 08.11.2016.
 */
public abstract class QuantMessage {

    protected String text;
    protected MessageAddress messageAddress;
    protected Locale locale = Locale.US;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MessageAddress getMessageAddress() {
        return messageAddress;
    }

    public void setMessageAddress (MessageAddress messageAddresses) {
        this.messageAddress = messageAddresses;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean hasText() {
        return text != null && !text.equals("");
    }
}
