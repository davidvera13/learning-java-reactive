package com.reactive.ws.ui.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
public class ResponseDto {
    private Date date;
    private int output;

    public ResponseDto(int output) {
        this.date = new Date();
        this.output = output;
    }
}
