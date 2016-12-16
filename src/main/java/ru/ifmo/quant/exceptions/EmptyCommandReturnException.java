package ru.ifmo.quant.exceptions;

/**
 * Created by andrey on 16.12.2016.
 */
public class EmptyCommandReturnException extends BadCommandReturnException {
    public EmptyCommandReturnException(String s) {
        super(s);
    }
}
