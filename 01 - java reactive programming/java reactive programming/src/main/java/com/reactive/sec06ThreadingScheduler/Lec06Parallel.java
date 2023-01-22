package com.reactive.sec06ThreadingScheduler;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class Lec06Parallel {
    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();

        Flux.range(1, 10)
                .parallel(3)
                .runOn(Schedulers.parallel())
                .doOnNext(i -> printThreadName("next " + i))
                .sequential()
                 //.subscribe(v -> printThreadName("sub " + v));
                //.subscribe(v -> integerList.add(v));
                // .subscribe(integerList::add);
                .subscribe(v -> {
                    printThreadName("sub " + v);
                    integerList.add(v);
                });

        Utils.sleepSeconds(5);
        // not thread safe...
        System.out.println(integerList);
        System.out.println(integerList.size());
    }

    private static void printThreadName(String message) {
        System.out.println("> Thread: " + Thread.currentThread().getName() + " > \t " + message);
    }
}
