package com.reactive.sec02.usecase;

import com.github.javafaker.Faker;
import com.reactive.utils.Utils;
import jdk.jshell.execution.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class StockPricePublisher {
    public static Flux<Integer> getPriceFlux() {

        AtomicInteger atomicInteger = new AtomicInteger(100);
        // return Flux.interval(Duration.ofMillis(500))
        //        .map(i -> atomicInteger.getAndAccumulate(
        //                        Utils.faker().random().nextInt(-5, 5),
        //                        (valA, valB) -> valA + valB));
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> atomicInteger.getAndAccumulate(
                        Utils.faker().random().nextInt(-5, 5),
                        Integer::sum));

    }
}
