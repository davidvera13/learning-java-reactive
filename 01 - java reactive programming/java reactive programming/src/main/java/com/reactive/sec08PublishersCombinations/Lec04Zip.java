package com.reactive.sec08PublishersCombinations;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec04Zip {
    public static void main(String[] args) {
        // we need a car body, a car engine and a set of 4 tires to build a car ..
        // we build up to 5 car body, 4 car engines, 12 sets of tires
        // we cannot build more than 4 cars completely
        Flux.zip(getCarBody(), getCarEngine(), getCarTires())
                .subscribe(Utils.subscriber());
    }

    private static Flux<String> getCarBody(){
        return Flux.range(1, 5)
                .map(i -> "body");
    }

    private static Flux<String> getCarEngine(){
        return Flux.range(1, 4)
        //return Flux.range(1, 3)
                .map(i -> "engine");
    }

    private static Flux<String> getCarTires(){
        return Flux.range(1, 12)
                .map(i -> "tires");
    }
}
