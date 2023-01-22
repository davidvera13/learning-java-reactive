package com.reactive.sec04Operators;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec03LimitRate {
    public static void main(String[] args) {
        // [ INFO] (main) | request(unbounded)
        // [ INFO] (main) | request(100)
        // When 75% is reached, limit rate is changed[ INFO] (main) | request(75)
        // [ INFO] (main) | onNext(75)
        Flux.range(0, 1000)
                .log()
                // .limitRate(100, 90) // we can change the default 75% value
                .limitRate(100, 0) // remove the limit of 75% of drained data
                .subscribe(Utils.subscriber());
    }
}
