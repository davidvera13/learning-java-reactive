package com.reactive.ws.ui.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CalculationRequestDto {
    private String operation;
    private int a;
    private int b;

}
