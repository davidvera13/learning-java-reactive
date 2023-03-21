package org.example.rsocket.lec01FireAndForget.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import reactor.core.publisher.Mono;

public class SocketAcceptorSlowImpl implements SocketAcceptor {
    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket sendingSocket) {
        System.out.println("SocketAcceptorSlowImpl > accept() called");
        // return Mono.fromCallable(() -> new MathService());
        return Mono.fromCallable(MathServiceSlow::new);
    }
}
