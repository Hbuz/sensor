package com.marco.api.sensor.exception;

public class NotValuesFoundException extends RuntimeException {
    public NotValuesFoundException() {
        super("No temperature values found");
    }
}