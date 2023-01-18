package com.reactive.utils;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;

import java.util.function.Consumer;

public class Utils {
    private static final Faker FAKER = Faker.instance();
    public static Consumer<Object> onNext() {
        return o -> System.out.println("Received " + o);
    }

    public static Consumer<Object> onError() {
        return e -> System.out.println("Exception " + e);
    }

    public static Runnable onComplete() {
        return () -> System.out.println("Completed...");
    }

    public static Faker faker() {
        return FAKER;
    }

    public static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Subscriber<Object> subscriber() {
        return new AppSubscriber();
    }
    public static Subscriber<Object> subscriber(String name) {
        return new AppSubscriber(name);
    }
}
