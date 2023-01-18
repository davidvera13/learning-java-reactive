package com.reactive.sec02;

import com.reactive.utils.Utils;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicReference;

public class Lec07FluxCustomSubscription {
    public static void main(String[] args) {

        final AtomicReference<Subscription> atomicReference = new AtomicReference<>();
        Flux.range(1, 20).log().subscribeWith(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("Received subscription: " + subscription);
                atomicReference.set(subscription);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext() called: " + integer);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError() called: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete() called ");
            }
        });

        Utils.sleepSeconds(3);
        atomicReference.get().request(3);
        Utils.sleepSeconds(5);
        atomicReference.get().request(3);
        Utils.sleepSeconds(5);
        System.out.println("Canceling subscription");
        atomicReference.get().cancel();
        Utils.sleepSeconds(2);
        // won't work as we canceled subscription
        atomicReference.get().request(4);
    }
}
