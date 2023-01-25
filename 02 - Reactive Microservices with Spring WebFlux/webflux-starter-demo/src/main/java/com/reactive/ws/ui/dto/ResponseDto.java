package com.reactive.ws.ui.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ResponseDto {
    private Date date;
    private int output;

    public ResponseDto(int output) {
        this.date = new Date();
        this.output = output;
    }
}
