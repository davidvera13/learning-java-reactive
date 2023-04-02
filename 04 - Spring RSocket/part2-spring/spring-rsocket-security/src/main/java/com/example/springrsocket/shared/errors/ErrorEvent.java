package com.example.springrsocket.shared.errors;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ErrorEvent {
    private StatusCode statusCode;
    private LocalDate localDate;

    public ErrorEvent(StatusCode statusCode, LocalDate localDate) {
        this.statusCode = statusCode;
        this.localDate = LocalDate.now();
    }

    public ErrorEvent(StatusCode statusCode) {
        this.statusCode = statusCode;
        this.localDate = LocalDate.now();
    }

    public ErrorEvent() {
        this.localDate = LocalDate.now();
    }
}
