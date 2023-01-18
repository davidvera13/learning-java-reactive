package com.reactive.sec02;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec09FluxInterval {
    public static void main(String[] args) {
        // non blocking async
        Flux.interval(Duration.ofSeconds(1))
                .subscribe(Utils.onNext());

        Utils.sleepSeconds(5);
    }
}
