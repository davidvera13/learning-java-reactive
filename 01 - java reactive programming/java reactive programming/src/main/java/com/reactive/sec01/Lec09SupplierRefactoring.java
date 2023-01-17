package com.reactive.sec01;

import com.reactive.utils.Utils;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;

public class Lec09SupplierRefactoring {
    public static void main(String[] args) {
        // note: Value is never used as Publisher
        // the pipeline (return) is built but not executed... lazy execution
        getName();
        
        // we subscribe and print from the supplier but we don't return the return value
        getName().subscribe();

        // we subscribe, print from supplier and onNext() allow to retrieve return value
        getName().subscribe(Utils.onNext());

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
