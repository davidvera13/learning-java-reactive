package com.reactive.sec11Sinks;

import com.reactive.utils.Utils;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Lec02SinkOneMultiSubscriber {
    public static void main(String[] args) {
        // mon can emit one value, empty or an error
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();

        // adding another subscriber: all subscribers receive the same data
        mono.subscribe(Utils.subscriber("John"));
        mono.subscribe(Utils.subscriber("Paul"));


        // we're emitting data
        sink.tryEmitValue("Hello World");
        // sink.tryEmitEmpty();
        // sink.tryEmitError(new RuntimeException("Oops something went wrong"));

        Utils.sleepSeconds(1);
    }
}
