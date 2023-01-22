package com.reactive.sec04Operators;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec05OnError {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .log()
                .map(integer -> 10 / (5 - integer)) // should retrieve a / by zero exception
                // .onErrorReturn(-1) // default value on error
                // .onErrorResume(e -> fallBack()) // default value returned by a fallback method
                // on error continue takes a biconsummer
                .onErrorContinue((err, obj) -> {
                    System.out.println("An exception was thrown (and we don't care) for value " + obj + " error is " + err.getMessage());
                })
                .subscribe(Utils.subscriber());
    }

    private static Mono<Integer> fallBack() {
        return Mono.fromSupplier(() -> Utils.faker().random().nextInt(100, 200));
    }
}
