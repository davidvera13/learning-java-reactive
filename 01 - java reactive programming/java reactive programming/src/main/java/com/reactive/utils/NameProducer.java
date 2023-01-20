package com.reactive.utils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class NameProducer implements Consumer<FluxSink<String>> {
    private FluxSink<String> fluxSink;
    @Override
    public void accept(FluxSink<String> stringFluxSink) {
        this.fluxSink = stringFluxSink;
    }

    public void produce() {
        String name = Utils.faker().name().fullName();
        String currentThreadName = Thread.currentThread().getName();
        this.fluxSink.next(currentThreadName + ": " + name);
    }

    @Override
    public Consumer<FluxSink<String>> andThen(Consumer<? super FluxSink<String>> after) {
        return Consumer.super.andThen(after);
    }
}
