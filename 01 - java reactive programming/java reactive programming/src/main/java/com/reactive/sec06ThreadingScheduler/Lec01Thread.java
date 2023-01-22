package com.reactive.sec06ThreadingScheduler;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec01Thread {
    public static void main(String[] args) {
        Flux<Object> flux = Flux
                .create(fluxSink -> {
                    printThreadName("Flux.create() called");
                    fluxSink.next(1);
                })
                .doOnNext(i -> printThreadName("next " + i));

        Runnable runnable = () -> flux.subscribe(v -> printThreadName("flux.subscribe() called: " + v));
        for (int i = 0; i < 5; i++) {
            new Thread(runnable).start();
        }
        Utils.sleepSeconds(5);

    }

    private static void printThreadName(String message) {
        System.out.println(Thread.currentThread().getName() + " : \t " + message);
    }
}
