package com.marco.api.sensor.exception;

public class NoValuesFoundException extends RuntimeException {
    public NoValuesFoundException() {
        super("No temperature values found");
    }
}