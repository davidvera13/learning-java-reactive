package org.example.rsocket.lec06RSocketConnexion.service;

import io.rsocket.Payload;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FreeService extends MathService {
    // implement fire and forget
    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        // free service won't access any data from MathService
        return Mono.empty();
    }
    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        // free service won't access any data from MathService
        return Mono.empty();
    }

    @Override
    public Flux<Payload> requestStream(Payload payload) {
        // limited functionality
        // does the same as parent class but takes 5 entries out of the whole result set
        return super.requestStream(payload).take(3);
    }

    @Override
    public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
        // limited functionality
        return super.requestChannel(payloads).take(3);
    }
}
