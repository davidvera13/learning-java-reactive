package com.reactive.sec02;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec01Flux {
    public static void main(String[] args) {
        // 0..1..n
        Flux<Integer> integerFlux = Flux.just(1);
        integerFlux.subscribe(Utils.onNext());

        System.out.println("*************");
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5);
        flux.subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

        System.out.println("*************");
        Flux<Integer> emptyFlux = Flux.empty();
        emptyFlux.subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

        System.out.println("*************");
        Flux<Object> objectFlux = Flux.just(1, 2, 3, "Hello World", Utils.faker().aquaTeenHungerForce().character());
        objectFlux.subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());
    }
}
