package com.reactive.sec03;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec04FluxCreateIssueFix {
    public static void main(String[] args) {
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
    }
}
