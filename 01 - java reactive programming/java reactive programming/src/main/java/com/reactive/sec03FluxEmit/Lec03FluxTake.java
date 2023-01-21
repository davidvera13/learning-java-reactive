package com.reactive.sec03FluxEmit;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec03FluxTake {
    public static void main(String[] args) {
        // map, filter are operators
        // once take complete its task, the subscription is canceled ...
        // the flux is stopping emitting data
        Flux.range(1, 10)
                .log()
                .take(3) // take up to 3 items
                .log()
                .subscribe(Utils.subscriber());
    }
}
