package com.example.trading.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserStockDto {
    private String id;
    private String userId;
    private String stockSymbol;
    private Integer quantity;

}
