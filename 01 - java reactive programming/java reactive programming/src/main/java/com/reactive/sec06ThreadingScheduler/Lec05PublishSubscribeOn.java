package com.reactive.sec06ThreadingScheduler;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec05PublishSubscribeOn {
    public static void main(String[] args) {
        Flux<Object> flux = Flux
                .create(fluxSink -> {
                    printThreadName("Flux.create() called");
                    for (int i = 0; i < 5; i++) {
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                })
                .doOnNext(i -> printThreadName("next " + i));


        flux
                .publishOn(Schedulers.parallel())
                .doOnNext(i -> printThreadName("next " + i))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(v -> printThreadName("sub " + v));

        Utils.sleepSeconds(5);
    }

    private static void printThreadName(String message) {
        System.out.println("> Thread: " + Thread.currentThread().getName() + " > \t " + message);
    }

}
