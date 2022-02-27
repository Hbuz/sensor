package com.marco.api.sensor.exception;

public class EmptyValueBulkException extends RuntimeException {
    public EmptyValueBulkException() {
        super("The temperature values bulk is empty");
    }
}