package com.reactive.sec01Mono;

import com.reactive.utils.Utils;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;

public class Lec08MonoFromCallable {
    public static void main(String[] args) {
        // Callable<String> stringCallable = () -> getName();
        Callable<String> stringCallable = Lec08MonoFromCallable::getName;
        Mono.fromCallable(stringCallable)
                .subscribe(Utils.onNext());
    }

    private static String getName() {
        System.out.println("Generating name ... ");
        return Utils.faker().name().fullName();
    }
}
