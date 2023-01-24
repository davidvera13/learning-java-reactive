package com.reactive.sec13UnitTest;

import com.reactive.sec09Batching.usecase1.BookOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class Lec05VirtualTimeTest {
    @Test
    void test1() {
        StepVerifier.create(timeConsumingFlux())
                .expectNext("1s", "2s", "3s", "4s", "5s")
                .verifyComplete();
    }

    @Test
    public void test2(){
        // StepVerifier.withVirtualTime(() -> timeConsumingFlux())
        StepVerifier.withVirtualTime(this::timeConsumingFlux)
                .expectSubscription() // sub is an event
                .expectNoEvent(Duration.ofSeconds(4))
                .thenAwait(Duration.ofSeconds(20))
                .expectNext("1s", "2s", "3s", "4s", "5s")
                .verifyComplete();
    }

    private Flux<String> timeConsumingFlux() {
        return Flux.range(1,5).delayElements(Duration.ofSeconds(5))
                .map(i -> i + "s");

    }
}
