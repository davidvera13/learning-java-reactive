package com.reactive.sec02;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec11FluxToMono {
    public static void main(String[] args) {
        // publish first item because converted to mono...
        Flux.range(1, 10)
                .log()
                // we can do some filter to select value to display. Let's consider value must be > 3 => 4 is returned
                .filter(i -> i > 3)
                .next()
                .subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

        System.out.println("***********");

        Flux.range(1, 10)
                .next()
                .log()
                // be careful... will return nothing... we select 1st element and filer after ... As 1 < 3, we go onComplete directly
                .filter(i -> i > 3)
                .subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());
    }

    private static void doSomething(Flux<String> flux) {
        flux.subscribe(Utils.onNext());
    }
}
