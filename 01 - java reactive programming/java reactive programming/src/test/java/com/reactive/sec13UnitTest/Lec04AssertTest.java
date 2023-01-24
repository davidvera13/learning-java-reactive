package com.reactive.sec13UnitTest;

import com.reactive.sec09Batching.usecase1.BookOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class Lec04AssertTest {
    @Test
    void test1() {
        // Assert test
        //Mono<BookOrder> bookOrder = Mono.fromSupplier(() -> new BookOrder());
        Mono<BookOrder> bookOrder = Mono.fromSupplier(BookOrder::new);

        StepVerifier.create(bookOrder)
                .assertNext(b -> Assertions.assertNotNull(b.getAuthor()))
                .verifyComplete();
    }

    @Test
    void test2() {
        // Assert test
        //Mono<BookOrder> bookOrder = Mono.fromSupplier(() -> new BookOrder());
        Mono<BookOrder> bookOrder = Mono.fromSupplier(BookOrder::new)
                .delayElement(Duration.ofSeconds(3));

        StepVerifier.create(bookOrder)
                .assertNext(b -> Assertions.assertNotNull(b.getAuthor()))
                .expectComplete()
                .verify(Duration.ofMillis(3500));
    }

}
