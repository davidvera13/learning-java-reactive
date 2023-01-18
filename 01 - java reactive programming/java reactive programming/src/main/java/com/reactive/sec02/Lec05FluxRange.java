package com.reactive.sec02;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec05FluxRange {
    public static void main(String[] args) {
        // for loop ??
        Flux.range(1, 10).subscribe(Utils.onNext());
        System.out.println("******************");
        Flux.range(3, 10).subscribe(Utils.onNext());

        System.out.println("******************");
        Flux.range(3, 10)
                // The flux iterate numbers, map associate each increment to a String
                .map(i -> Utils.faker().name().fullName())
                // the subscription is on map returned value
                .subscribe(
                        next -> System.out.println("Received " + next),
                        throwable -> System.out.println("Oops " + throwable.getMessage()),
                        () -> System.out.println("Done"));
    }
}
