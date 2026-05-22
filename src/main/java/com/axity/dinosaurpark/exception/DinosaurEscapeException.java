package com.axity.dinosaurpark.exception;

public class DinosaurEscapeException extends Exception {
    private final String dinosaurName;

    public DinosaurEscapeException(String dinosaurName, String message) {
        super(message);
        this.dinosaurName = dinosaurName;
    }

    public String getDinosaurName() {
        return dinosaurName;
    }
}