package com.reactive.sec09Batching;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import javax.crypto.spec.PSource;
import java.time.Duration;

public class Lec04GroupBy {
    public static void main(String[] args) {
        Flux.range(1,30)
                .delayElements(Duration.ofSeconds(1))
                .groupBy(i -> i % 2) // key is 1 or 0
                .subscribe(groupedFlux -> process(groupedFlux, groupedFlux.key()));

        Utils.sleepSeconds(20);
    }

    private static void process(Flux<Integer> flux, int key) {
        System.out.println("process() was called");
        flux.subscribe(i -> System.out.println("Key " + key + " item: " + i));
    }
}
