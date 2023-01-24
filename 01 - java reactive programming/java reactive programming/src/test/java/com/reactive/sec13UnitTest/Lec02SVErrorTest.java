package com.reactive.sec13UnitTest;

import com.reactive.sec09Batching.usecase1.BookOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec02SVErrorTest {
    @Test
    void test1() {
        // check error
        Flux<Integer> just = Flux.just(1, 2, 3);
        Flux<Integer> error = Flux.error(new RuntimeException("Oops"));
        Flux<Integer> concat = Flux.concat(just, error);


        StepVerifier.create(concat)
                .expectNext(1, 2, 3)
                .verifyError();
    }

    @Test
    void test2() {
        // check error: we can pass exception expected
        Flux<Integer> just = Flux.just(1, 2, 3);
        Flux<Integer> error = Flux.error(new RuntimeException("Oops"));
        Flux<Integer> concat = Flux.concat(just, error);


        StepVerifier.create(concat)
                .expectNext(1, 2, 3)
                .verifyError(RuntimeException.class);
    }

    @Test
    void test3() {
        // check error: we can pass exception message
        Flux<Integer> just = Flux.just(1, 2, 3);
        Flux<Integer> error = Flux.error(new RuntimeException("Oops"));
        Flux<Integer> concat = Flux.concat(just, error);


        StepVerifier.create(concat)
                .expectNext(1, 2, 3)
                .verifyErrorMessage("Oops");
    }
}
