package org.example.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.example.rsocket.client.CallbackService;
import org.example.rsocket.commons.dto.RequestDto;
import org.example.rsocket.commons.helper.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec03RSocketCallbackPeerToPeerTest {
    private RSocket rSocket;

    @BeforeAll
    public void setup() {
        // setup connection with server
        this.rSocket = RSocketConnector.create()
                .acceptor(SocketAcceptor.with(new CallbackService()))
                .connect(TcpClientTransport.create("localhost", 8585))
                .block();
    }


    @Test
    public void callback() throws InterruptedException {
        RequestDto requestDto = new RequestDto(5);
        Mono<Void> mono = this.rSocket.fireAndForget(Utils.toPayload(requestDto));

        StepVerifier
                .create(mono)
                .verifyComplete();

        System.out.println("Waiting...");
        // block thread
        Thread.sleep(12000);
    }
}
