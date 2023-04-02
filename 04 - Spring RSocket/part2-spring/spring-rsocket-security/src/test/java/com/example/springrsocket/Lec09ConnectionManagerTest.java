package com.example.springrsocket;


import com.example.springrsocket.shared.dto.ComputationRequestDto;
import com.example.springrsocket.shared.dto.ComputationResponseDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
@TestPropertySource(properties = { "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration" })
public class Lec09ConnectionManagerTest {

    @Autowired
    private RSocketRequester.Builder builder;


    @Test
    void connectionSetup() throws InterruptedException {

        // we setup 2 connections
        RSocketRequester r1 = this.builder
                .transport(TcpClientTransport.create("localhost", 6565));

        RSocketRequester r2 = this.builder
                .transport(TcpClientTransport.create("localhost", 6565));

        RSocketRequester r3 = this.builder
                .setupRoute("math.events.connection")
                .transport(TcpClientTransport.create("localhost", 6565));

        r1.route("math.service.print")
                .data(new ComputationRequestDto(12))
                .send()
                .subscribe();

        r2.route("math.service.print")
                .data(new ComputationRequestDto(13))
                .send()
                .subscribe();

        r3.route("math.service.print")
                .data(new ComputationRequestDto(14))
                .send()
                .subscribe();

        Thread.sleep(10000);

    }
}
