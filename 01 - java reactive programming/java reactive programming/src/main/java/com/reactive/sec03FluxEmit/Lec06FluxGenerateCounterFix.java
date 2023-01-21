package com.reactive.sec03FluxEmit;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec06FluxGenerateCounterFix {
    public static void main(String[] args) {
        // counter is embedded in the flux generate
        Flux.generate(
                () -> 1, // defining the state, here the initial state is the start value of counter
                (counter, sink) -> {
                    String country = Utils.faker().country().name();
                    System.out.println("emitting -> #" + counter + " " + country);
                    sink.next(country);
                    if(country.equalsIgnoreCase("canada") || counter >= 10)
                        sink.complete();
                    return  counter + 1;
                })
                // .take(4)
                .subscribe(Utils.subscriber());
    }
}
