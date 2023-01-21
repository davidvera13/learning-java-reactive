package com.reactive.sec01Mono;

import com.reactive.utils.Utils;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public class Lec07MonoFromSupplier {
    public static void main(String[] args) {
        // use when the data is ready to use ...
        // we're not retrieving fullName
        // Mono<String> mono = Mono.just(getName());

        Mono<String> monoFromSupplier = Mono.fromSupplier(() -> getName());
        monoFromSupplier.subscribe(Utils.onNext());

        // Supplier<String> stringSupplier = () -> getName();
        Supplier<String> stringSupplier = Lec07MonoFromSupplier::getName;
        Mono<String> otherMonoFromSupplier = Mono.fromSupplier(stringSupplier);
        otherMonoFromSupplier.subscribe(Utils.onNext());
    }

    private static String getName() {
        System.out.println("Generating name ... ");
        return Utils.faker().name().fullName();
    }
}
