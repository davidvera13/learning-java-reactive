package com.reactive.sec10RepeatAndRetry;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec01Repeat {
    private static final AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {
        getIntegers()
                .repeat(2) // 2 more time -> will be executed 3 times
                .subscribe(Utils.subscriber());
    }

    private static Flux<Integer> getIntegers() {
        return Flux.range(1,3)
                .doOnSubscribe(subscription -> System.out.println("Subscribed"))
                .doOnComplete(() -> System.out.println("-- Completed --"))
                .map(i -> atomicInteger.getAndIncrement());
    }
}
