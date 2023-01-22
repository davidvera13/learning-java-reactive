package com.reactive.sec04Operators;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.concurrent.Queues;

import java.time.Duration;

public class Lec04Delay {
    // Queues in package reactor.util.concurrent;
    public static void main(String[] args) {
        // System.setProperty("reactor.bufferSize.x", "9");
        Flux.range(1, 100)  // 32
                .log()
                .delayElements(Duration.ofSeconds(1), Schedulers.parallel())
                .subscribe(Utils.subscriber());



        Utils.sleepSeconds(60);
    }
}
