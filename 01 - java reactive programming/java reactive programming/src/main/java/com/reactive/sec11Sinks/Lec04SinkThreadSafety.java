package com.reactive.sec11Sinks;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Lec04SinkThreadSafety {
    public static void main(String[] args) {
        // handler through which we can emit items
        Sinks.Many<Object> sink = Sinks.many()
                .unicast()
                .onBackpressureBuffer();

        // handler through which subscribers will receive items
        Flux<Object> flux = sink.asFlux();

        List<Object> list = new ArrayList<>();

        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            CompletableFuture.runAsync(() -> {
                // not having expected result
                // sink.tryEmitNext(finalI);
                // will repeat operation if fails
                sink.emitNext(finalI, (signalType, emitResult) -> true);
            });
        }

        Utils.sleepSeconds(2);
        // we do not get 1000
        System.out.println(list.size());




    }

}
