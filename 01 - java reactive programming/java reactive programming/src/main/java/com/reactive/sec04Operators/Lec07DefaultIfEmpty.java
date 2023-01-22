package com.reactive.sec04Operators;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec07DefaultIfEmpty {
    public static void main(String[] args) {
        getNumbers()
                .filter(i -> i > 10)
                .defaultIfEmpty(-100)
                .subscribe(Utils.subscriber());

        System.out.println("******************");
        getNumbers2()
                .filter(i -> i > 10)
                .defaultIfEmpty(-100)
                .subscribe(Utils.subscriber());

        System.out.println("******************");
        getNumbers()
                .filter(i -> i > 10)
                .switchIfEmpty(fallBack())
                .subscribe(Utils.subscriber());

        System.out.println("******************");
        getNumbers2()
                .filter(i -> i > 10)
                .switchIfEmpty(fallBack())
                .subscribe(Utils.subscriber());
    }

    private static Flux<Integer> getNumbers() {
        return Flux.range(1, 15);
    }

    private static Flux<Integer> getNumbers2() {
        return Flux.range(1, 10);
    }

    private static Flux<Integer> fallBack() {
        return Flux.range(20, 5);
    }
}
