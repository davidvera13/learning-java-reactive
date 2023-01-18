package com.reactive.sec02.helper;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class NameGenerator {
    public static String getName() {
        Utils.sleepSeconds(1);
        return Utils.faker().name().fullName();
    }

    public static List<String> getNames(int count) {
        List<String> list = new ArrayList<>(count);
        for (int i = 0; i < count ; i++) {
            list.add(getName());
        }
        return list;
    }

    public static Flux<String> getFluxNames(int count) {
        // for int i = 0 to count, call getName() into a Flux...
        return Flux.range(0, count)
                .map(i -> getName());
    }
}
