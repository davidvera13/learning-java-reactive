package com.example.springrsocket;

import com.example.springrsocket.shared.dto.ComputationRequestDto;
import com.example.springrsocket.shared.dto.ComputationResponseDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.time.Duration;

@SpringBootTest
@TestPropertySource(properties = { "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration" })
public class Lec14SslTest {


    @Autowired
    private RSocketRequester.Builder builder;


    static {
        System.setProperty("javax.net.ssl.trustStore", "src/main/resources/client.truststore");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");

    }

    @Test
    void sslTest() throws InterruptedException {
        RSocketRequester r1 = this.builder
               // .transport(TcpClientTransport.create("localhost", 6565));
                .transport(TcpClientTransport.create(
                        TcpClient.create().host("localhost").port(6565).secure()
                ));

        for(int i = 0; i < 50; i++) {
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
