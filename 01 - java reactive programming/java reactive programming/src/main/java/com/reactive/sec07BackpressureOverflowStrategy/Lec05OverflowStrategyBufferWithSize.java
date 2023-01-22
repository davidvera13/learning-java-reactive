package com.reactive.sec07BackpressureOverflowStrategy;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class Lec05OverflowStrategyBufferWithSize {
    // AppSubscriberName ERROR: The receiver is overrun by more signals than expected (bounded queue...)
    public static void main(String[] args) {
        // 2 threads involved
        // emitted : 500 - published :  256 (by default)
        // we can change number of published elements in buffer

        // AppSubscriberName Received: 15
        // Pushed 126..
        // Pushed 133
        // AppSubscriberName Received: 97
        // values were drop automatically

        System.setProperty("reactor.bufferSize.small", "16");
        List<Object> list = new ArrayList<>();

        Flux
                .create(fluxSink -> {
                    for (int i = 0; i < 201 && !fluxSink.isCancelled(); i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed " + i);
                        Utils.sleepMilliseconds(1);
                    }
                    fluxSink.complete();
                }) // , FluxSink.OverflowStrategy.BUFFER
                .onBackpressureBuffer(64, o -> System.out.println("> Dropped " + o))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> Utils.sleepMilliseconds(10))
                .subscribe(Utils.subscriber());

        Utils.sleepSeconds(15);

    }

}
