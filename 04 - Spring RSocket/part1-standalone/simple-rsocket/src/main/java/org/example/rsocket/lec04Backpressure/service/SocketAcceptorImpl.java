package org.example.rsocket.lec04Backpressure.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import reactor.core.publisher.Mono;

public class SocketAcceptorImpl implements SocketAcceptor {
    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket sendingSocket) {
        sendingSocket.fireAndForget(null);
        System.out.println("SocketAcceptorImpl > accept() called");
        return Mono.fromCallable(FastProducerService::new);
    }
}
