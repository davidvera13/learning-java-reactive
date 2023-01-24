package com.reactive.sec13UnitTest;

import com.reactive.sec09Batching.usecase1.BookOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec03SVRangeTest {

    @Test
    void test1() {
        // range test
        Flux<Integer> range = Flux.range(1, 50);

        StepVerifier.create(range)
                .expectNextCount(50)
                .verifyComplete();
    }

    @Test
    void test2() {
        // range test
        Flux<Integer> range = Flux.range(1, 49);

        StepVerifier.create(range)
                .expectNextCount(50)
                .verifyComplete();
    }

    @Test
    void test3() {
        // range test
        Flux<Integer> range = Flux.range(1, 51);

        StepVerifier.create(range)
                .expectNextCount(50)
                .verifyComplete();
    }

    @Test
    void test4() {
        // range test
        Flux<Integer> range = Flux.range(1, 50);

        StepVerifier.create(range)
                .thenConsumeWhile(i -> i < 100)
                .verifyComplete();
    }
    @Test
    void test5() {
        // range test and failed condition
        Flux<Integer> range = Flux.range(1, 50)
                .map(i -> i * 2);

        StepVerifier.create(range)
                .thenConsumeWhile(i -> i < 100)
                .verifyComplete();
    }
}
