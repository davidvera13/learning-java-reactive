package com.example.stock.controller;

import com.example.stock.domain.StockTickDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockControllerTest {
    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeEach
    void setUp() {
    }

    @Test
    void stockPriceTest() {
        RSocketRequester requester = this.builder
                .transport(TcpClientTransport.create("localhost", 7070));

        Flux<StockTickDto> flux = requester.route("stocks.ticks")
                .retrieveFlux(StockTickDto.class)
                .doOnNext(System.out::println)
                .take(20);

        StepVerifier
                .create(flux)
                .expectNextCount(20)
                .verifyComplete();
    }
}