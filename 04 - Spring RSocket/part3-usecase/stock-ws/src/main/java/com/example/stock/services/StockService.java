package com.example.stock.services;

import com.example.stock.domain.StockTickDto;
import reactor.core.publisher.Flux;

public interface StockService {
    Flux<StockTickDto> getStockPrice();
}
