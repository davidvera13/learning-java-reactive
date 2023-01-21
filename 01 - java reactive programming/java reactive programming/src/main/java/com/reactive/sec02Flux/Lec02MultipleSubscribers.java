package com.reactive.sec02Flux;

import reactor.core.publisher.Flux;

public class Lec02MultipleSubscribers {
    public static void main(String[] args) {
        Flux<Integer> integerFlux =  Flux.just(1,2,3,4,5,6,7);

        // multiple sequential subscribers
        integerFlux.subscribe(i -> System.out.println("Sub1: " + i));
        integerFlux.subscribe(i -> System.out.println("Sub2: " + i));

        Flux<Integer> evenFlux = integerFlux.filter(i -> i % 2 == 0);
        evenFlux.subscribe(i -> System.out.println("Sub3: " + i));
    }
}
