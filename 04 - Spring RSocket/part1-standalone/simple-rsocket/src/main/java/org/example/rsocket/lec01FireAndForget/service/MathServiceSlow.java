package org.example.rsocket.lec01FireAndForget.service;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Mono;

public class MathServiceSlow implements RSocket {
    // implement fire and forget
    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        // let's make server running slow by adding a thread sleep for handling data
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Receiving slowly " + payload.getDataUtf8());
        return Mono.empty();
    }
}
