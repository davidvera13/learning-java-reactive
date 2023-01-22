package com.reactive.sec09Batching;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class Lec03Window {
    private static AtomicInteger atomicInteger = new AtomicInteger(1);
    public static void main(String[] args) {
        eventStream()
                // we can pass duration as window parameter
                 //.window(Duration.ofSeconds(2))
                .window(5)
                // Will create a flux of a flux...
                //.map(flux -> saveEvents(flux))
                .flatMap(Lec03Window::saveEvents)
                .subscribe(Utils.subscriber());

        Utils.sleepSeconds(20);
    }

    private static Flux<String> eventStream() {
        // emitting string stream
        return Flux
                .interval(Duration.ofMillis(500))
                .map(i -> "event" + i);
    }

    private static Mono<Integer> saveEvents(Flux<String> flux) {
        return flux
                .doOnNext(e -> System.out.println("saving " + e))
                .doOnComplete(() -> System.out.println("Saved this batch \n ------------------------------------"))
                .then(Mono.just(atomicInteger.getAndIncrement()));
    }
}
