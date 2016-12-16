package ru.ifmo.quant.exceptions;

/**
 * Created by andrey on 16.12.2016.
 */
public class NullCommandReturnException extends BadCommandReturnException {
    public NullCommandReturnException(String s) {
        super(s);
    }
}
