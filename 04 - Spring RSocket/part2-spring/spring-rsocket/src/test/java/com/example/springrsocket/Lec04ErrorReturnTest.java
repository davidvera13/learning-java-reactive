package com.example.springrsocket;

import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec04ErrorReturnTest {
    private RSocketRequester requester;

    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeAll
    public void setup(){
        this.requester = this.builder
                .transport(TcpClientTransport.create("localhost", 6565));
    }

    // fire and forget
    @Test
    void printDouble() {
        Mono<Integer> mono = this.requester
                .route("math.error.printdouble.20")
                .retrieveMono(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void printDoubleThrow() {
        Mono<Integer> mono = this.requester
                .route("math.error.printdouble.47")
                .retrieveMono(Integer.class)
                .doOnNext(System.out::println);

        // no value will be returned because of filter
        StepVerifier.create(mono)
                .expectNextCount(0)
                .verifyComplete();
    }


    @Test
    void printDoubleWithDefaultIfEmpty() {
        Mono<Integer> mono = this.requester
                .route("math.error.printdouble.defaultIfEmtpy.47")
                .retrieveMono(Integer.class)
                .doOnNext(System.out::println);

        // no value will be returned because of filter
        StepVerifier.create(mono)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }


    @Test
    void printDoubleWithDefaultSwitchValue() {
        Mono<Integer> mono = this.requester
                .route("math.error.printdouble.switchIfEmpty.47")
                .retrieveMono(Integer.class)
                .onErrorReturn(Integer.MIN_VALUE)
                .doOnNext(System.out::println);

        // no value will be returned because of filter
        StepVerifier.create(mono)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

}
