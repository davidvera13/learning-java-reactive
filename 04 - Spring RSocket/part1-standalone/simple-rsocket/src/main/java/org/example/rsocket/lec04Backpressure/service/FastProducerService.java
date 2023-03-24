package org.example.rsocket.lec04Backpressure.service;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Flux;

public class FastProducerService implements RSocket {

    @Override
    public Flux<Payload> requestStream(Payload payload) {
        // we don't care abbout what we send back, we send a lot of results
        return Flux.range(1, 1000)
                .map(i -> i + "") // just returning the value ...
                .doOnNext(System.out::println)
                .doFinally(System.out::println)
                .map(DefaultPayload::create);
    }
}
