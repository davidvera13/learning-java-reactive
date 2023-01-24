package com.reactive.sec11Sinks;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class Lec07SinkManyMulticastDirectAllOrNothing {
    public static void main(String[] args) {
        System.setProperty("reactor.bufferSize.small", "16");

        // handle through which we would push items
        Sinks.Many<Object> sink = Sinks.many().multicast().directAllOrNothing();

        // handle through which subscribers will receive items
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Utils.subscriber("sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Utils.subscriber("mike"));

        for (int i = 0; i < 100; i++) {
            sink.tryEmitNext(i);
        }

        Utils.sleepSeconds(10);
    }
}
