package com.example.springrsocket;

import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec03InputValidationTest {
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
                .route("math.validation.printdouble.20")
                .retrieveMono(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void printDoubleThrow() {
        Mono<Integer> mono = this.requester
                .route("math.validation.printdouble.47")
                .retrieveMono(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }
}
