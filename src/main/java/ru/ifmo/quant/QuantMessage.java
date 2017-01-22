package ru.ifmo.quant;

import org.telegram.telegrambots.api.objects.Location;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

/**
 * Created by andrey on 08.11.2016.
 */
public abstract class QuantMessage {

    protected String text;
    protected MessageAddress messageAddress;
    protected KeyboardEnum keyboard;
    protected Long date;
    protected Location location;

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

    public boolean hasText() {
        return text != null && !text.equals("");
    }

    public boolean isKeyboard() {
        if (keyboard == null) return false;
        return true;
    }

    public KeyboardEnum getKeyboard() {
        return keyboard;
    }

    public QuantMessage setKeyboard(KeyboardEnum keyboard) {
        this.keyboard = keyboard;
        return this;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setDate(Integer date) {
        this.date = date.longValue();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (!o.getClass().equals(this.getClass())) return false;

        QuantMessage message = (QuantMessage) o;
        if (!message.getText().equals(this.getText())) return false;
        if (!message.getMessageAddress().equals(this.getMessageAddress())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (messageAddress != null ? messageAddress.hashCode() : 0);
        return result;
    }
}
