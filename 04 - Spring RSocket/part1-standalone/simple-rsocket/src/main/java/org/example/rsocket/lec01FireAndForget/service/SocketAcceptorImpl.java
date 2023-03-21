package org.example.rsocket.lec01FireAndForget.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import reactor.core.publisher.Mono;

public class SocketAcceptorImpl implements SocketAcceptor {
    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket sendingSocket) {
        System.out.println("SocketAcceptorImpl > accept() called");
        // return Mono.fromCallable(() -> new MathService());
        return Mono.fromCallable(MathService::new);
    }
}
