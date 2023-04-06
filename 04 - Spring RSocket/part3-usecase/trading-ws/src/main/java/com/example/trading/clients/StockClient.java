package com.example.trading.clients;

import com.example.trading.domain.*;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.rsocket.RSocketConnectorConfigurer;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class StockClient {
    private RSocketRequester requester;
    private Flux<StockTickDto> flux;
    private final Map<String, Float> map;

    @Autowired
    public StockClient(RSocketRequester.Builder builder,
                       RSocketConnectorConfigurer connectorConfigurer,
                       @Value("${stocks.services.host}") String host,
                       @Value("${stocks.services.port}") int port) {
        this.requester = builder.rsocketConnector(connectorConfigurer)
                .transport(TcpClientTransport.create(host, port));
        this.map = new HashMap<>();
        this.initialize();
    }

    public Flux<StockTickDto> getStocksStream() {
        // hot publisher ....
        return this.flux;
    }
    private void initialize() {
        this.flux = this.requester
                .route("stocks.ticks")
                .retrieveFlux(StockTickDto.class)
                .doOnNext(stockTickDto -> map.put(stockTickDto.getCode(), stockTickDto.getPrice()))
                .retryWhen(Retry.fixedDelay(Long.MAX_VALUE, Duration.ofSeconds(2)))
                .publish()
                .autoConnect(0);
    }

    public float getCurrentStockPrice(String stockSymbol) {
        return this.map.getOrDefault(stockSymbol, 0f);
    }


//    public Mono<TransactionResponse> doTransaction(TransactionRequest transactionRequest) {
//        return this.requester.route("users.transactions")
//                .data(transactionRequest)
//                .retrieveMono(TransactionResponse.class)
//                .doOnNext(System.out::println);
//    }
//
//    public Flux<UserDto> getUsers() {
//        return this.requester.route("users.get.all")
//                .retrieveFlux(UserDto.class);
//    }
}
