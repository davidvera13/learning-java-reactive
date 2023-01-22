package com.reactive.sec07BackpressureOverflowStrategy;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec01OverflowStrategyIntroduction {
    public static void main(String[] args) {
        // 2 threads involved
        Flux
                .create(fluxSink -> {
                    for (int i = 0; i < 501; i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed " + i);
                    }
                    fluxSink.complete();
                })
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> Utils.sleepMilliseconds(25))
                .subscribe(Utils.subscriber());

        Utils.sleepSeconds(30);
    }
}
