package com.marco.api.sensor.exception;

public class EmptyValueException extends RuntimeException {
    public EmptyValueException() {
        super("The temperature value is empty");
    }
}