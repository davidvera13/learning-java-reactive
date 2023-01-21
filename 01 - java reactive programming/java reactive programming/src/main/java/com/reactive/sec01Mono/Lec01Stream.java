package com.reactive.sec01Mono;

import java.util.stream.Stream;

public class Lec01Stream {
    public static void main(String[] args) {
        // create a stream
        Stream<Integer> stream =  Stream.of(1)
                .map(val -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return 2 * val;
                });

        // System.out.printf(stream.toString());
        stream.forEach(System.out::println);
    }
}
