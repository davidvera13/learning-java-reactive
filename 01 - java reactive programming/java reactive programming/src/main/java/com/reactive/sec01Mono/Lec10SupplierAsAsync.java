package com.reactive.sec01Mono;

import com.reactive.utils.Utils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Lec10SupplierAsAsync {
    public static void main(String[] args) {

        // behavior is different because of Schedulers
        getName();
        getName()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(Utils.onNext());
        getName();

        // we do not see any result, we can use sleep
        Utils.sleepSeconds(4);

    }


    private static Mono<String> getName() {
        System.out.println("getName() was called");
        return Mono.fromSupplier(() -> {
            System.out.println("Generating name ... ");
            Utils.sleepSeconds(3);
            return Utils.faker().name().fullName();
        }).map(String::toUpperCase);
    }
}
