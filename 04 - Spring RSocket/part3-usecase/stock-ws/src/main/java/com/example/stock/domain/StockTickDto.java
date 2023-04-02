package com.example.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor @NoArgsConstructor
public class StockTickDto {
    private String code;
    private float price;
    private LocalDate timeStamp;
}
