package com.reactive.sec03;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec06FluxGenerateCounter {
    public static void main(String[] args) {
        // allow to use only 1 synchronousSink object but will emit non stop
        // exit if we have Canada or if emitted 10 countries maximum
        // will emit until
        Flux
                .create(fluxSink -> {
                    String country;
                    int counter = 0;
                    do {
                        counter++;
                        country = Utils.faker().country().name();
                        System.out.println("emitting -> #" + counter + " " + country);
                        fluxSink.next(country);
                    } while (
                            !country.equalsIgnoreCase("canada") && counter < 10);
                    fluxSink.complete();
                })
                .subscribe(Utils.subscriber());

        System.out.println("********************************************************");

        // with flux generate
        // this solution will would but, we could update atomicInteger outside the Flux and it would impact the process
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Flux
                .generate(synchronousSink -> {
                    // adding counter in here won't work, it will be reinitialized each time
                    // int counter = 0;
                    atomicInteger.getAndIncrement();
                    String country = Utils.faker().country().name();
                    System.out.println("emitting -> #" + atomicInteger.get() + " " + country);
                    synchronousSink.next(country);
                    if(country.equalsIgnoreCase("canada") || atomicInteger.get() >= 10)
                        synchronousSink.complete();
                })
                // .take(3)
                .subscribe(Utils.subscriber());
    }
}
