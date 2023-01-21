package com.reactive.sec01Mono;

import reactor.core.publisher.Mono;

public class Lec02MonoJust {
    public static void main(String[] args) {
        // publisher
        Mono<Integer> mono = Mono.just(1);

        // nothing happens until we subscribe
        System.out.println(mono);

        mono.subscribe(val -> System.out.println("Received " + val));
    }
}
