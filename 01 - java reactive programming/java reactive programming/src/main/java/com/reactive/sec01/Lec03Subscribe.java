package com.reactive.sec01;

import reactor.core.publisher.Mono;

public class Lec03Subscribe {
    public static void main(String[] args) {
        // publisher
        Mono<String> mono = Mono.just("Hello World");

        // nothing happens
        // mono.subscribe();

        mono.subscribe(
                item -> System.out.println(item),
                err -> System.out.println(err.getMessage()),
                () -> System.out.println("Completed")
        );

        mono.subscribe(
                System.out::println, // with method reference
                err -> System.out.println(err.getMessage()),    // On error
                () -> System.out.println("Completed")
        );

        Mono<Integer> integerMono = Mono.just("Hello World")
                .map(String::length)
                .map(len -> len / 0); // should generate exception

        // same code as bellow but we enter the exception
        integerMono.subscribe(
                System.out::println, // with method reference
                err -> System.out.println(err.getMessage()),    // On error
                () -> System.out.println("Completed")
        );
    }
}
