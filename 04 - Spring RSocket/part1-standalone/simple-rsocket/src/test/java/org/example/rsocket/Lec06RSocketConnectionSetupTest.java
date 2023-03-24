package org.example.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
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
public class Lec06RSocketConnectionSetupTest {
    private RSocketClient rSocketClient;
    private RSocketClient rSocketFreeClient;

    @BeforeAll
    public void setup(){
        Mono<RSocket> socketMono = RSocketConnector.create()
                .setupPayload(DefaultPayload.create("user:password"))
                .connect(TcpClientTransport.create("localhost", 9595))
                .doOnNext(r -> System.out.println("going to connect"));

        this.rSocketClient = RSocketClient.from(socketMono);


        Mono<RSocket> socketFreeMono = RSocketConnector.create()
                .setupPayload(DefaultPayload.create("user:oops"))
                .connect(TcpClientTransport.create("localhost", 9595))
                .doOnNext(r -> System.out.println("going to connect"));

        this.rSocketFreeClient = RSocketClient.from(socketFreeMono);




    }

    @Test
    public void accountValid()  {
        Payload payload = Utils.toPayload(new RequestDto(5));

        Flux<ResponseDto> flux = this.rSocketClient
                .requestStream(Mono.just(payload))
                .map(p -> Utils.toObject(p, ResponseDto.class))
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();

    }

    @Test
    public void accountInvalid()  {
        Payload payload = Utils.toPayload(new RequestDto(5));

        Flux<ResponseDto> flux = this.rSocketFreeClient
                .requestStream(Mono.just(payload))
                .map(p -> Utils.toObject(p, ResponseDto.class))
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(3)
                .verifyComplete();

    }
}
