package com.reactive.sec09Batching;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec02OverlapAndDrop {
    public static void main(String[] args) {
        // skip value can be greater than buffer size. It makes skip data from a first batch to the second
        // if skip = 5 > [event0, event1, event2]  [event5, event6, event7]  [event10, event11, event12]
        eventStream()
                .buffer(3, 1)
                .subscribe(Utils.subscriber());

        Utils.sleepSeconds(20);
    }

    private static Flux<String> eventStream() {
        // emitting string stream
        return Flux
                .interval(Duration.ofMillis(800))
                // .take(4)  // will be completed after first data reception
                .map(i -> "event" + i);
    }
}
