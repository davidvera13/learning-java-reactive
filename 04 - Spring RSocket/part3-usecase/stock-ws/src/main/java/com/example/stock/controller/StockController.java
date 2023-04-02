package com.example.stock.controller;

import com.example.stock.domain.StockTickDto;
import com.example.stock.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class StockController {
    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @MessageMapping("stocks.ticks")
    public Flux<StockTickDto> stockPrice() {
        return this.stockService.getStockPrice();
    }
}
