package com.reactive.sec06ThreadingScheduler;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec03MultipleSubscribeOn {
    public static void main(String[] args) {
        Flux<Object> flux = Flux
                .create(fluxSink -> {
                    printThreadName("Flux.create() called");
                    for (int i = 0; i < 20; i++) {
                        fluxSink.next(i);
                        Utils.sleepSeconds(1);
                    }
                })
                //.subscribeOn(Schedulers.newParallel("customParallel"))
                .doOnNext(i -> printThreadName("next " + i));


        flux
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(v -> printThreadName("flux.subscribe() called: " + v));

        flux
                .subscribeOn(Schedulers.parallel())
                .subscribe(v -> printThreadName("flux.subscribe() parallel called: " + v));

        // Runnable runnable = () -> flux
        //        .subscribeOn(Schedulers.boundedElastic())
        //        // .subscribeOn(Schedulers.parallel())
        //        .subscribe(v -> printThreadName("flux.subscribe() called: " + v));

        //for (int i = 0; i < 5; i++) {
        //    new Thread(runnable).start();
        //}
        Utils.sleepSeconds(5);

    }

    private static void printThreadName(String message) {
        System.out.println("> Thread: " + Thread.currentThread().getName() + " > \t " + message);
    }
}
