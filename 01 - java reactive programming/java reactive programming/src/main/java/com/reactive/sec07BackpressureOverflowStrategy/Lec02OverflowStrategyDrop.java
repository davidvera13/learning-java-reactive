package com.reactive.sec07BackpressureOverflowStrategy;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
// see queues params
import reactor.util.concurrent.Queues;

import java.util.ArrayList;
import java.util.List;

public class Lec02OverflowStrategyDrop {
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
                    for (int i = 0; i < 201; i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed " + i);
                        Utils.sleepMilliseconds(1);
                    }
                    fluxSink.complete();
                })
                // .onBackpressureBuffer()
                .onBackpressureDrop(list::add) // we can store dropped data to an object
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> Utils.sleepMilliseconds(10))
                .subscribe(Utils.subscriber());

        Utils.sleepSeconds(15);
        System.out.println("Dropped values");
        System.out.println(list);
        System.out.println(list.size());
    }

}
