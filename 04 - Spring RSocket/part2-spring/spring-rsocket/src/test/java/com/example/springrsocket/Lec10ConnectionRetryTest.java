package com.example.springrsocket;


import com.example.springrsocket.shared.dto.ClientConnectionRequestDto;
import com.example.springrsocket.shared.dto.ComputationRequestDto;
import com.example.springrsocket.shared.dto.ComputationResponseDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
@TestPropertySource(properties = { "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration" })
public class Lec10ConnectionRetryTest {
    @Autowired
    private RSocketRequester.Builder builder;


    @Test
    void connectionSetup() throws InterruptedException {

        // we can handle retry connexion
        RSocketRequester r1 = this.builder
                .rsocketConnector(connector -> connector
                        .reconnect(Retry
                                .fixedDelay(100, Duration.ofSeconds(2))
                                .doBeforeRetry(s -> System.out.println("Retrying " + s.totalRetriesInARow()))))
                .transport(TcpClientTransport.create("localhost", 6565));

        for(int i = 0; i < 50; i++) {
            // we will stop server and we should have error ... AbstractChannel$AnnotatedConnectException
            Mono<ComputationResponseDto> mono = r1.route("math.service.calculateSquare")
                    .data(new ComputationRequestDto(i))
                    .retrieveMono(ComputationResponseDto.class)
                    .doOnNext(System.out::println);
            StepVerifier.create(mono)
                    .expectNextCount(1)
                    .verifyComplete();

            Thread.sleep(2000);
        }


    }
}
