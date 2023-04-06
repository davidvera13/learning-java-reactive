package com.example.trading.services;

import com.example.trading.domain.StockTradeRequest;
import com.example.trading.domain.UserStockDto;
import com.example.trading.io.entity.UserStockEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserStockService {
    Mono<UserStockEntity> buyStock(StockTradeRequest request);

    Mono<UserStockEntity> sellStock(StockTradeRequest request);

    Flux<UserStockDto> getUserStocks(String userId);
}
