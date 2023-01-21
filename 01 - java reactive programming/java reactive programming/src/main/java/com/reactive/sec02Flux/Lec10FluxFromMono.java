package com.reactive.sec02Flux;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec10FluxFromMono {
    public static void main(String[] args) {
        Mono<String> mono = Mono.just("Hello I'm a mono... ");
        // conversion here
        Flux<String> flux = Flux.from(mono);
        doSomething(flux);
    }

    private static void doSomething(Flux<String> flux) {
        flux.subscribe(Utils.onNext());
    }
}
