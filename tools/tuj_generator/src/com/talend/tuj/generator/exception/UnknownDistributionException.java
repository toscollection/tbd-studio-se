package com.talend.tuj.generator.exception;

public class UnknownDistributionException extends Exception {
    public UnknownDistributionException() {
        this("Unknwon distribution");
    }

    public UnknownDistributionException(String message) {
        super(message);
    }
}
