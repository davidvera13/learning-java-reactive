package org.example.rsocket.lec01.service;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Mono;

public class MathService implements RSocket {
    // implement fire and forget

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        // communicate with byte array - for sending string, we can use getDataUtf8
        System.out.println("Receiving " + payload.getDataUtf8());
        // return RSocket.super.fireAndForget(payload);
        return Mono.empty();
    }
}
