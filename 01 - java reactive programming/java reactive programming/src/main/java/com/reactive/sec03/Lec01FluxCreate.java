package com.reactive.sec03;

import com.reactive.utils.AppSubscriber;
import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec01FluxCreate {
    public static void main(String[] args) {
        // fluxSink is the Consumer / emitter
        Flux.create(fluxSink -> {
            for (int i = 10; i >= 3; i--) {
                fluxSink.next(i);
            }
            System.out.println("*************");
            fluxSink.next(1); // emit 1
            fluxSink.next(3); // emit 2
            fluxSink.complete();
        }).subscribe(Utils.onNext());

        Flux.create(fluxSink -> {
            String country;
            do {
                country = Utils.faker().country().name();
                fluxSink.next(country);
            } while (!country.equalsIgnoreCase("canada"));
            //for (int i = 0; i < 10; i++) {
            //    fluxSink.next(Utils.faker().country().name());
            // }
            fluxSink.complete();
        }).subscribe(Utils.subscriber());

        System.out.println("*************");
        Flux.create(fluxSink -> {
            String country;
            do {
                country = Utils.faker().country().name();
                fluxSink.next(country);
            } while (country.length() > 6);

            fluxSink.complete();
        }).subscribe(Utils.subscriber("CountrySubscriber"));
    }
}
