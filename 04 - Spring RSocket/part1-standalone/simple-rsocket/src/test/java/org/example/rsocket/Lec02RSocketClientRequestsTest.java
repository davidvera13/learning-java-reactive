package org.example.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.example.rsocket.commons.dto.ChartResponseDto;
import org.example.rsocket.commons.dto.RequestDto;
import org.example.rsocket.commons.dto.ResponseDto;
import org.example.rsocket.commons.helper.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec02RSocketClientRequestsTest {
    private RSocket rSocket;


    @BeforeAll
    public void setup() {
        this.rSocket = RSocketConnector.create()
                .connect(TcpClientTransport.create("localhost", 7575))
                .block();
    }


    @Test
    public void requestResponse() {
        Payload payload = Utils.toPayload(new RequestDto(42));
        Mono<ResponseDto> mono = this.rSocket.requestResponse(payload)
                .map(responsePayload -> Utils.toObject(responsePayload, ResponseDto.class))
                .doOnNext(System.out::println);

        StepVerifier
                .create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void requestStream() {
        Payload payload = Utils.toPayload(new RequestDto(5));
        Flux<ResponseDto> flux = this.rSocket.requestStream(payload)
                .map(responsePayload -> Utils.toObject(responsePayload, ResponseDto.class))
                .doOnNext(System.out::println);

        StepVerifier
                .create(flux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void requestStreamTakeNthElements() {
        Payload payload = Utils.toPayload(new RequestDto(5));
        Flux<ResponseDto> flux = this.rSocket.requestStream(payload)
                .map(responsePayload -> Utils.toObject(responsePayload, ResponseDto.class))
                .doOnNext(System.out::println)
                .take(5);  // will take nfirst elements;

        StepVerifier
                .create(flux)
                .expectNextCount(5)
                .verifyComplete();
    }


    @Test
    public void requestChannel() {
        Flux<Payload> payloadFlux = Flux.range(-10, 21)
                .delayElements(Duration.ofMillis(500))
                .map(RequestDto::new)
                .map(Utils::toPayload);

        Flux<ChartResponseDto> flux = this.rSocket.requestChannel(payloadFlux)
                .map(responsePayload -> Utils.toObject(responsePayload, ChartResponseDto.class))
                .doOnNext(System.out::println);

        StepVerifier
                .create(flux)
                .expectNextCount(21)
                .verifyComplete();
    }

}
