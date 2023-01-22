package com.reactive.sec08PublishersCombinations;

import com.reactive.sec08PublishersCombinations.helper.NameGenerator;
import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec02ConcatWith {
    public static void main(String[] args) {
        Flux<String> flux1 = Flux.just("a", "b", "c");
        Flux<String> flux2 = Flux.just("d", "e", "f", "g", "h");
        Flux<String> flux3 = Flux.error(new RuntimeException("Oops, something went wrong"));

        Flux<String> concatWithFlux = flux1.concatWith(flux2);
        concatWithFlux.subscribe(Utils.subscriber());

        System.out.println("*******************");
        // error will stop the workflow
        Flux<String> concatFlux = Flux.concat(flux1, flux3, flux2);
        concatFlux.subscribe(Utils.subscriber());

        System.out.println("*******************");
        // fix error behaviour
        Flux<String> concatDelayedErrorsFlux = Flux.concatDelayError(flux1, flux3, flux2);
        concatDelayedErrorsFlux.subscribe(Utils.subscriber());
    }
}
