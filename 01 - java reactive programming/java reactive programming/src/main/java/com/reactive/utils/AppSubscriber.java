package com.reactive.utils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class AppSubscriber implements Subscriber<Object> {
    private String name;

    public AppSubscriber(String name) {
        this.name = name;
    }

    public AppSubscriber() {
        this.name = "AppSubscriberName";
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Object o) {
        System.out.println(this.name + " Received: " + o);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(this.name + " ERROR: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println(this.name + " Completed");
    }
}
