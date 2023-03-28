package com.example.springrsocket;

import com.example.springrsocket.shared.dto.ComputationRequestDto;
import com.example.springrsocket.shared.dto.ComputationResponseDto;
import io.rsocket.core.Resume;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.time.Duration;

@SpringBootTest
@TestPropertySource(properties = { "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration" })
public class Lec12LoadBalancingTest {

    @Autowired
    private RSocketRequester.Builder builder;


    @Test
    void connectionSetup() throws InterruptedException {

        // we setup 3 connections
        // calling nginx
        RSocketRequester r1 = this.builder
                .transport(TcpClientTransport.create("localhost", 6566));

        RSocketRequester r2 = this.builder
                .transport(TcpClientTransport.create("localhost", 6566));

        RSocketRequester r3 = this.builder
                .setupRoute("math.events.connection")
                .transport(TcpClientTransport.create("localhost", 6566));

        for (int i = 0; i < 50; i++) {

            r1.route("math.service.print")
                    .data(new ComputationRequestDto(i))
                    .send()
                    .subscribe();

            r2.route("math.service.print")
                    .data(new ComputationRequestDto(i))
                    .send()
                    .subscribe();

            r3.route("math.service.print")
                    .data(new ComputationRequestDto(i))
                    .send()
                    .subscribe();

            Thread.sleep(10000);
        }

    }
}
