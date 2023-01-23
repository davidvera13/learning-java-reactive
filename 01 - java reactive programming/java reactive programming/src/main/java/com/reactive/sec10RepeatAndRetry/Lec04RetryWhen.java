package com.reactive.sec10RepeatAndRetry;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class Lec04RetryWhen {

    public static void main(String[] args) {
        getIntegers()
                .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(3)))
                .subscribe(Utils.subscriber());

        Utils.sleepSeconds(20);
    }

    private static Flux<Integer> getIntegers() {
        return Flux.range(1,3)
                .doOnSubscribe(subscription -> System.out.println("Subscribed"))
                .doOnComplete(() -> System.out.println("-- Completed --"))
                .map(i -> i / (Utils.faker().random().nextInt(1, 5) > 3 ? 0 : 1))
                .doOnError(err -> System.out.println("-- error --"));
    }
}
