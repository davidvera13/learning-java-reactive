package org.example.rsocket.lec01.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import reactor.core.publisher.Mono;

public class SocketAcceptorObjectsImpl implements SocketAcceptor {
    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket sendingSocket) {
        System.out.println("SocketAcceptorSlowImpl > accept() called");
        // return Mono.fromCallable(() -> new MathService());
        return Mono.fromCallable(MathServiceObjects::new);
    }
}
