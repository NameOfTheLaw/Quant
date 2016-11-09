package ru.ifmo.quant.commands;

/**
 * Created by andrey on 09.11.2016.
 */
public class CommandFactory {
    //TODO: realise class logic

    public QuantCommand build(String body) {
        if (body.toLowerCase().equals("start")) {
            return new StartCommand();
        }
        if (body.toLowerCase().equals("today")) {
            return new TodayCommand();
        }
        return null;
    }
}
