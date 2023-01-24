package com.reactive.sec11Sinks;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Lec01SinkOne {
    public static void main(String[] args) {
        // mon can emit one value, empty or an error
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        mono.subscribe(Utils.subscriber("John"));

        // we're emitting data
        // sink.tryEmitValue("Hello World");
        // sink.tryEmitEmpty();
        // sink.tryEmitError(new RuntimeException("Oops something went wrong"));

        // do try exception

        sink.emitValue("Hello world", (signalType, emitResult) -> {
            // we can do a retry based on params passed in callback
            System.out.println("Signal type: " + signalType.name());
            System.out.println("Emit result" + emitResult.name());
            return false;
        });

        // we should not be able to emit value a second time
        sink.emitValue("Hello world", (signalType, emitResult) -> {
            // we can do a retry based on params passed in callback
            System.out.println("Signal type: " + signalType.name());
            System.out.println("Emit result: " + emitResult.name());
            // if we pass true, it will retry to emit
            return false;
        });

        Utils.sleepSeconds(1);
    }
}
