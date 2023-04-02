package com.example.stock.services.impl;

import com.example.stock.domain.Stock;
import com.example.stock.domain.StockTickDto;
import com.example.stock.services.StockService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDate;

@Service
public class StockServiceImpl implements StockService {
    private static final Stock AMZN = new Stock(1000, "AMZN", 20);
    private static final Stock AAPL = new Stock(1250, "APPL", 20);
    private static final Stock MSFT = new Stock(475, "MSFT", 20);

    @Override
    public Flux<StockTickDto> getStockPrice() {
        // we send data every 2 seconds, with updated prices ...
        return Flux.interval(Duration.ofSeconds(2))
                .flatMap(i -> Flux.just(AMZN, AAPL, MSFT))
                .map(stock -> new StockTickDto(stock.getCode(), stock.getPrice(), LocalDate.now()));
    }
}
