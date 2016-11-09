package ru.ifmo.quant;

import java.util.List;

/**
 * Created by andrey on 08.11.2016.
 */
public abstract class QuantMessage {

    protected String text;
    protected MessageAddress messageAddress;

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
}
