package com.reactive.sec02Flux;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec06FluxLog {
    public static void main(String[] args) {
        Flux.range(3, 10)
                .log() // [ INFO] (main) | onNext(7)
                // The flux iterate numbers, map associate each increment to a String
                .map(i -> Utils.faker().name().fullName())
                .log() // [ INFO] (main) | onNext(Dr. Orpha Huels)
                // the subscription is on map returned value
                .subscribe(
                        next -> System.out.println("Received " + next),
                        throwable -> System.out.println("Oops " + throwable.getMessage()),
                        () -> System.out.println("Done"));
    }
}
