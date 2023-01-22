package com.reactive.sec06ThreadingScheduler;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class Lec07FluxInterval {
    public static void main(String[] args) {
        // interval uses parallel schedulers
        Flux.interval(Duration.ofSeconds(1))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(Utils.subscriber());

        Utils.sleepSeconds(5);
    }
}
