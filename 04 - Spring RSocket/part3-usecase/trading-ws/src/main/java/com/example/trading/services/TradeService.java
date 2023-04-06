package com.example.trading.services;

import com.example.trading.domain.StockTradeRequest;
import com.example.trading.domain.StockTradeResponse;
import reactor.core.publisher.Mono;

public interface TradeService {
    Mono<StockTradeResponse> trade(StockTradeRequest request);
}
