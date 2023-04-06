package com.example.trading.controller;

import com.example.trading.clients.StockClient;
import com.example.trading.domain.StockTickDto;
import com.example.trading.domain.StockTradeRequest;
import com.example.trading.domain.StockTradeResponse;
import com.example.trading.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("stocks")
public class TradeController {
    private final TradeService tradeService;
    private final StockClient stockClient;

    @Autowired
    public TradeController(TradeService tradeService, StockClient stockClient) {
        this.tradeService = tradeService;
        this.stockClient = stockClient;
    }

    @PostMapping("trades")
    public Mono<ResponseEntity<StockTradeResponse>> trade(@RequestBody Mono<StockTradeRequest> tradeRequestMono) {
        return tradeRequestMono
                .filter(stockTradeRequest -> stockTradeRequest.getQuantity() > 0)
                .flatMap(this.tradeService::trade)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping(value = "tick/streams", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StockTickDto> stockTickDto() {
        return this.stockClient.getStocksStream();
    }



}
