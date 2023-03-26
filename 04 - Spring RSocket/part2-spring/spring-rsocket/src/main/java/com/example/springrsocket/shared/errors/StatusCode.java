package com.example.springrsocket.shared.errors;

public enum StatusCode {
    EC001("Given number is not within expected range"),
    EC002("Your usage limit exceeded");

    private final String message;

    StatusCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
