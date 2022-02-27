package com.marco.api.sensor.exception;

public class ValueNotValidException extends RuntimeException {
    public ValueNotValidException(String parameter) {
        super("The value of " + parameter + " is not valid");
    }
}