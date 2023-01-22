package com.reactive.sec04Operators;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec06TimeOut {
    public static void main(String[] args) {
        getOrderedNumbers()
                .timeout(Duration.ofSeconds(2), fallBack())
                .subscribe(Utils.subscriber());

        Utils.sleepSeconds(30);

    }

    private static Flux<Integer> getOrderedNumbers() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(5));
    }

    private static Flux<Integer> fallBack() {
        return Flux.range(100, 10)
                //.delayElements(Duration.ofMillis(250));
                .delayElements(Duration.ofSeconds(5));
    }
}
