package com.example.trading.domain;

import com.example.trading.domain.enums.TradingType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StockTradeRequest {
    private String userId;
    private String stockSymbol;
    private int quantity;
    private TradingType tradingType;
}
