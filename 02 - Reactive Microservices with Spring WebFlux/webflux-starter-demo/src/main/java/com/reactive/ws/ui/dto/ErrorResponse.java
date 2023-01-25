package com.reactive.ws.ui.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ErrorResponse {
    private int errorCode;
    private int input;
    private String message;
}
