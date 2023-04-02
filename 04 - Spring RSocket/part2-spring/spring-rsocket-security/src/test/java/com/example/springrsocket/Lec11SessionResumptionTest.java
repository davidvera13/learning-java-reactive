package com.example.springrsocket;

import com.example.springrsocket.shared.dto.ChartResponseDto;
import com.example.springrsocket.shared.dto.ComputationRequestDto;
import com.example.springrsocket.shared.dto.ComputationResponseDto;
import io.rsocket.core.Resume;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
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

@SpringBootTest
@TestPropertySource(properties = { "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration" })
public class Lec11SessionResumptionTest {
    @Autowired
    private RSocketRequester.Builder builder;



    // request stream
    @Test
    void multiplicationTableStream() {
        RSocketRequester requester = this.builder
                // retry strategy won't work if we stop server... we need to handle resume
                .rsocketConnector(c -> c
                        .resume(resumeStrategy())
                        .reconnect(retryStrategy()))
                .transport(TcpClientTransport.create("localhost", 6566));

        Flux<ComputationResponseDto> flux = requester
                .route("math.service.tableStream")
                .data(new ComputationRequestDto(4))
                .retrieveFlux(ComputationResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(10000)
                .verifyComplete();
    }

    private Retry retryStrategy() {
        return Retry.fixedDelay(120, Duration.ofSeconds(1))
                .doBeforeRetry(s -> System.out.println("Retry connection " + s.totalRetriesInARow()));
    }

    private Resume resumeStrategy() {
        return new Resume()
                .retry(Retry.fixedDelay(2000, Duration.ofSeconds(2))
                        .doBeforeRetry(s -> System.out.println("Resume retry strategy " + s)));
    }
}
