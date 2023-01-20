package com.reactive.sec03;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec04FluxCreateIssueFix {
    public static void main(String[] args) {
        // only one instance of flux sink will be generated
        Flux
                .create(fluxSink -> {
                    String country;
                    do {
                        country = Utils.faker().country().name();
                        System.out.println("emitting -> " + country);
                        fluxSink.next(country);
                    } while (
                            !country.equalsIgnoreCase("canada") && !fluxSink.isCancelled());
                    // to solve emission even if take is complete
                    fluxSink.complete();
                })
                .take(3) // subscriber stops receiving, but we're still emitting ...
                .subscribe(Utils.subscriber());

        System.out.println("********************************************************");
        // will emit until
        Flux
                .create(fluxSink -> {
                    String country;
                    do {
                        country = Utils.faker().country().name();
                        System.out.println("emitting -> " + country);
                        fluxSink.next(country);
                    } while (!country.equalsIgnoreCase("canada"));
                    fluxSink.complete();
                })
                .subscribe(Utils.subscriber());

        System.out.println("********************************************************");

        // with flux generate
        Flux
                .generate(synchronousSink -> {
                    String country = Utils.faker().country().name();
                    System.out.println("emitting -> " + country);
                    synchronousSink.next(country);
                    if(country.equalsIgnoreCase("canada"))
                        synchronousSink.complete();
                })
                // .take(3)
                .subscribe(Utils.subscriber());

    }
}
