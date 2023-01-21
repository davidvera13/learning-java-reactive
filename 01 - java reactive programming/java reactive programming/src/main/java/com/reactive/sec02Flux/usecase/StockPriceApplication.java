package com.reactive.sec02Flux.usecase;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

public class StockPriceApplication {
    public static void main(String[] args) throws InterruptedException {
        // act like a counter
        CountDownLatch latch = new CountDownLatch(1);

        StockPricePublisher.getPriceFlux()
                .log().subscribeWith(new Subscriber<Integer>() {
                    private Subscription subscription;
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer price) {
                        System.out.println(LocalDateTime.now() + " - Price: " + price);
                        if(price > 110 || price < 90) {
                            this.subscription.cancel();
                            // reduce the countdown
                            latch.countDown();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        // reduce the countdown
                        latch.countDown();
                    }

                    @Override
                    public void onComplete() {
                        // reduce the countdown
                        latch.countDown();
                    }
                });

        latch.await();
    }
}
