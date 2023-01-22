package com.reactive.sec08PublishersCombinations.helper;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.util.ArrayList;
import java.util.List;

public class NameGenerator {

    List<String> names = new ArrayList<>();

    public Flux<String> generateNames() {
        return Flux
                .generate((SynchronousSink<String> synchronousSink) -> {
                    System.out.println("Generate name");
                    Utils.sleepSeconds(1);
                    String name = Utils.faker().name().fullName();
                    // caching the names in a list to reuse the values
                    names.add(name);
                    synchronousSink.next(name);
                })
                .cast(String.class)
                .startWith(getNamesFromCache());
    }

    private Flux<String> getNamesFromCache() {
        return Flux.fromIterable(names);
    }
}
