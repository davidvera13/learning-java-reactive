package org.example.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec05RSocketPersistentConnectionTest {
    private RSocket rSocket;

    private RSocketClient rSocketClient;

    @BeforeAll
    public void setup() {
        // setup connection with server
        this.rSocket = RSocketConnector.create()
                .connect(TcpClientTransport.create("localhost", 9595))
                .block();

        Mono<RSocket> rSocketMono = RSocketConnector.create()
                .connect(TcpClientTransport.create("localhost", 9595))
                .doOnNext(r -> System.out.println("Connecting rSocket " + r));

        rSocketClient = RSocketClient.from(rSocketMono);
    }


    @Test
    public void connectionTest() throws InterruptedException {
        Flux<String> flux = this.rSocket.requestStream(DefaultPayload.create(""))
                .map(Payload::getDataUtf8)
                .delayElements(Duration.ofMillis(300))    // slowing artificially the process
                .take(10)
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();
    }

    // RESTART server side for test purpose
    // this method will crash : the rsocket represents the connection
    @Test
    public void multipleFluxConnection() throws InterruptedException {
        // server may need to restard for any reason ...
        Flux<String> f1 = this.rSocket.requestStream(DefaultPayload.create(""))
                .map(Payload::getDataUtf8)
                .delayElements(Duration.ofMillis(300))    // slowing artificially the process
                .take(10)
                .doOnNext(System.out::println);

        StepVerifier.create(f1)
                .expectNextCount(10)
                .verifyComplete();

        System.out.println("Sleeping... ");
        Thread.sleep(15000);
        System.out.println("Waking up... ");

        // second request ...
        Flux<String> f2 = this.rSocket.requestStream(DefaultPayload.create(""))
                .map(Payload::getDataUtf8)
                .delayElements(Duration.ofMillis(300))    // slowing artificially the process
                .take(10)
                .doOnNext(System.out::println);

        StepVerifier.create(f2)
                .expectNextCount(10)
                .verifyComplete();
    }


    // RESTART server side for test purpose
    // connection will be rebuilt
    @Test
    public void multipleFluxConnectionFix() throws InterruptedException {
        // server may need to restard for any reason ...
        Flux<String> f1 = this.rSocketClient
                .requestStream(Mono.just(DefaultPayload.create("")))
                .map(Payload::getDataUtf8)
                .delayElements(Duration.ofMillis(300))    // slowing artificially the process
                .take(10)
                .doOnNext(System.out::println);

        StepVerifier.create(f1)
                .expectNextCount(10)
                .verifyComplete();

        System.out.println("Sleeping... ");
        Thread.sleep(15000);
        System.out.println("Waking up... ");

        // second request ...
        Flux<String> f2 = this.rSocketClient
                .requestStream(Mono.just(DefaultPayload.create("")))
                .map(Payload::getDataUtf8)
                .delayElements(Duration.ofMillis(300))    // slowing artificially the process
                .take(10)
                .doOnNext(System.out::println);

        StepVerifier.create(f2)
                .expectNextCount(10)
                .verifyComplete();
    }
}
