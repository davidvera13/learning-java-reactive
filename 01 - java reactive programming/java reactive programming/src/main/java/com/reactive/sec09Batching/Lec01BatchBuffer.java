package com.reactive.sec09Batching;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec01BatchBuffer {
    public static void main(String[] args) {
        eventStream()
                // buffer with max items to handle
                // .buffer(5)
                // buffer with duration only will have to handle too many events
                // .buffer(Duration.ofSeconds(5))
                // we will wait 5 seconds, or 4 seconds to return data
                .bufferTimeout(5, Duration.ofSeconds(4))// return a list of string

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
