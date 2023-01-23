package com.reactive.sec09Batching.usecase2;

import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * order service publish a new purchase order every 100ms
 */
public class OrderService {
    public static Flux<PurchaseOrder> orderStream(){
        return Flux.interval(Duration.ofMillis(100))
                .map(i -> new PurchaseOrder());
    }
}
