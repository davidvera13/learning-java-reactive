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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec08ConnectionSetupHandlerTest {
    private RSocketRequester requester;

    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeAll
    public void setup(){
        // adding request body
        ClientConnectionRequestDto clientConnectionRequestDto = new ClientConnectionRequestDto();
        clientConnectionRequestDto.setClientId("123456");
        // clientConnectionRequestDto.setClientSecret("password");
        clientConnectionRequestDto.setClientSecret("passwordINVALID");

        this.requester = this.builder
                .setupData(clientConnectionRequestDto)
                .transport(TcpClientTransport.create("localhost", 6565));
    }

    @RepeatedTest(3)
    void connectionSetup() {
        Mono<ComputationResponseDto> mono = this.requester.route("math.service.calculateSquare")
                .data(new ComputationRequestDto(ThreadLocalRandom.current().nextInt(1, 50)))
                .retrieveMono(ComputationResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }
}
