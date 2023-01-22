package com.reactive.sec04Operators;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec01Handle {
    public static void main(String[] args) {
        // handle = filter + map
        Flux.range(0, 20)
                .handle(((integer, synchronousSink) -> {
                    if(integer == 7)
                        synchronousSink.complete();
                    // filter
                    if(integer % 2 == 0)
                        synchronousSink.next(integer);
                    // map
                    else
                        synchronousSink.next(integer + "mapping");
                }))
                .subscribe(Utils.subscriber());

        System.out.println("******************");
        Flux
                .generate(synchronousSink -> {
                    synchronousSink.next(Utils.faker().country().name());
                })
                .map(Object::toString)
                .handle((str, synchronousSink) -> {
                    synchronousSink.next(str);
                    if(str.equalsIgnoreCase("canada"))
                        synchronousSink.complete();
                })
                .subscribe(Utils.subscriber());

    }



}
