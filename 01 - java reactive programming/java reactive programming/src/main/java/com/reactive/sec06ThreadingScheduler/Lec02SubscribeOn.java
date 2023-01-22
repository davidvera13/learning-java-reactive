package com.reactive.sec06ThreadingScheduler;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec02SubscribeOn {
    public static void main(String[] args) {
        Flux<Object> flux = Flux
                .create(fluxSink -> {
                    printThreadName("Flux.create() called");
                    fluxSink.next(1);
                })
                .doOnNext(i -> printThreadName("next " + i));

        flux
                .doFirst(() -> printThreadName("doFirst() call #1"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printThreadName("doFirst() call #2"))
                .subscribe(v -> printThreadName("flux.subscribe() called: " + v));

        Utils.sleepSeconds(3);
        System.out.println("*************************");
        Runnable runnable = () -> flux
                .doFirst(() -> printThreadName("doFirst() call #1"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printThreadName("doFirst() call #2"))
                .subscribe(v -> printThreadName("flux.subscribe() called: " + v));

        for (int i = 0; i < 5; i++) {
            new Thread(runnable).start();


        }

        Utils.sleepSeconds(5);

    }

    private static void printThreadName(String message) {
        System.out.println("> Thread: " + Thread.currentThread().getName() + " > \t " + message);
    }
}
