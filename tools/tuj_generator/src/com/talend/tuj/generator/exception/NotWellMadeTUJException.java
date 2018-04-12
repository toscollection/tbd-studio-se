package com.talend.tuj.generator.exception;

public class NotWellMadeTUJException extends Exception {
    public NotWellMadeTUJException() {
        this("TUJ is not well made");
    }

    public NotWellMadeTUJException(String message) {
        super(message);
    }
}
