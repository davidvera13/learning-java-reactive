package com.reactive.sec03FluxEmit;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec05FluxGenerate {
    public static void main(String[] args) {
        // allow to use only 1 synchronousSink object but will emit non stop
        Flux
                .generate(synchronousSink -> {
                    System.out.println("Emitting ... ");
                    synchronousSink.next(Utils.faker().country().name());
                    // AppSubscriberName ERROR: More than one call to onNext
                    // synchronousSink.next(Utils.faker().country().name());
                    // synchronousSink.complete();
                    // synchronousSink.error(new RuntimeException("Ooops"));
                })
                .take(3)
                .subscribe(Utils.subscriber());
    }
}
