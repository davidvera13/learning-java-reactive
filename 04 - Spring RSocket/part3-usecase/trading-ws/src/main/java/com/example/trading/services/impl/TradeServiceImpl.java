package com.example.trading.services.impl;

import com.example.trading.clients.StockClient;
import com.example.trading.clients.UserClient;
import com.example.trading.domain.StockTradeRequest;
import com.example.trading.domain.StockTradeResponse;
import com.example.trading.domain.TransactionRequest;
import com.example.trading.domain.enums.TradingStatus;
import com.example.trading.domain.enums.TradingType;
import com.example.trading.domain.enums.TransactionStatus;
import com.example.trading.services.TradeService;
import com.example.trading.services.UserStockService;
import com.example.trading.shared.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TradeServiceImpl implements TradeService {
    private final UserStockService service;
    private final UserClient userClient;
    private final StockClient stockClient;


    @Autowired
    public TradeServiceImpl(UserStockService service, UserClient userClient, StockClient stockClient) {
        this.service = service;
        this.userClient = userClient;
        this.stockClient = stockClient;
    }

    @Override
    public Mono<StockTradeResponse> trade(StockTradeRequest request) {
        TransactionRequest transactionRequest = TransactionMapper.toTransactionRequest(request, this.estimatePrice(request));
        Mono<StockTradeResponse>  stockTradeResponseMono = TradingType.BUY.equals(request.getTradingType()) ?
                buyStock(request, transactionRequest) :
                sellStock(request, transactionRequest);

        return stockTradeResponseMono
                .defaultIfEmpty(TransactionMapper.toTradeResponse(request, TradingStatus.FAILED, 0));


    }

    private float estimatePrice(StockTradeRequest request) {
        return request.getQuantity()  * this.stockClient.getCurrentStockPrice(request.getStockSymbol());
    }

    private Mono<StockTradeResponse> buyStock(StockTradeRequest stockTradeRequest, TransactionRequest transactionRequest) {
        return this.userClient.doTransaction(transactionRequest)
                .filter(transactionResponse -> TransactionStatus.COMPLETED.equals(transactionResponse.getStatus()))
                .flatMap(transactionResponse -> this.service.buyStock(stockTradeRequest))
                .map(userStockEntity -> TransactionMapper.toTradeResponse(
                        stockTradeRequest,
                        TradingStatus.COMPLETED,
                        transactionRequest.getAmount()));
    }
    private Mono<StockTradeResponse> sellStock(StockTradeRequest stockTradeRequest, TransactionRequest transactionRequest) {
        // check if stocks are sufficient
        return this.service.sellStock(stockTradeRequest)
                .flatMap(userStockEntity -> this.userClient.doTransaction(transactionRequest))
                .map(transactionResponse -> TransactionMapper.toTradeResponse(
                        stockTradeRequest,
                        TradingStatus.COMPLETED,
                        transactionRequest.getAmount()));
    }
}
