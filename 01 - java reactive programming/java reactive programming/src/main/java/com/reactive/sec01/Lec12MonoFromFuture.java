package com.reactive.sec01;

import com.reactive.utils.Utils;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class Lec12MonoFromFuture {
    public static void main(String[] args) {
        Mono.fromFuture(getName()).subscribe(Utils.onNext());
        Utils.sleepSeconds(1);
    }

    private static CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> Utils.faker().name().fullName());
    }
}
