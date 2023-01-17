package com.reactive.sec01;

import com.reactive.utils.Utils;
import reactor.core.publisher.Mono;

public class Lec04MonoSubscribeRefactor {
    public static void main(String[] args) {
        // publisher
        Mono<String> mono = Mono.just("Hello World");

        // nothing happens
        // mono.subscribe();

        mono.subscribe(
                Utils.onNext(),
                Utils.onError(),
                Utils.onComplete()
        );

        mono.subscribe(
                Utils.onNext(),
                Utils.onError(),
                Utils.onComplete()
        );

        Mono<Integer> integerMono = Mono.just("Hello World")
                .map(String::length)
                .map(len -> len / 0); // should generate exception

        // same code as bellow but we enter the exception
        integerMono.subscribe(
                Utils.onNext(),
                Utils.onError(),
                Utils.onComplete()
        );
    }
}
