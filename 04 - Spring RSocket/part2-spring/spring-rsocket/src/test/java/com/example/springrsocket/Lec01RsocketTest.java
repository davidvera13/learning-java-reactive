package com.example.springrsocket;

import com.example.springrsocket.shared.dto.ChartResponseDto;
import com.example.springrsocket.shared.dto.ComputationRequestDto;
import com.example.springrsocket.shared.dto.ComputationResponseDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec01RsocketTest {
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
    void print() {
        Mono<Void> mono = this.requester
                .route("math.service.print")
                .data(new ComputationRequestDto(4))
                .send();

        StepVerifier.create(mono)
                .verifyComplete();
    }

    // request response
    @Test
    void calculateSquare() {
        Mono<ComputationResponseDto> mono = this.requester
                .route("math.service.calculateSquare")
                .data(new ComputationRequestDto(4))
                .retrieveMono(ComputationResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

    // request stream
    @Test
    void multiplicationTableStream() {
        Flux<ComputationResponseDto> flux = this.requester
                .route("math.service.multiplicationTableStream")
                .data(new ComputationRequestDto(4))
                .retrieveFlux(ComputationResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();

    }

    // request channel
    @Test
    void chartStream() {
        Flux<ComputationRequestDto> requestDtoFlux = Flux.range(-10, 21)
                .map(ComputationRequestDto::new);

        Flux<ChartResponseDto> flux = this.requester
                .route("math.service.chartStream")
                .data(requestDtoFlux)
                .retrieveFlux(ChartResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(21)
                .verifyComplete();

    }

}
