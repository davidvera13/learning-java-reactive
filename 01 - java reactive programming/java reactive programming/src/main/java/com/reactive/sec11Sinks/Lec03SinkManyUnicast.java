package com.reactive.sec11Sinks;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class Lec03SinkManyUnicast {
    public static void main(String[] args) {
        // handler through which we can emit items
        Sinks.Many<Object> sink = Sinks.many()
                .unicast()
                .onBackpressureBuffer();

        // handler through which subscribers will receive items
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Utils.subscriber("Walter"));
        // will throw: Fox ERROR: Sinks.many().unicast() sinks only allow a single Subscriber
        flux.subscribe(Utils.subscriber("Fox"));

        sink.tryEmitNext("Hello world !");
        sink.tryEmitNext("how are you ?!");
        sink.tryEmitNext("learning java reactive !");
    }
}
