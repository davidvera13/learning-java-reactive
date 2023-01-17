package com.reactive.utils;

import java.util.function.Consumer;

public class Utils {
    public static Consumer<Object> onNext() {
        return o -> System.out.println("Received " + o);
    }

    public static Consumer<Object> onError() {
        return e -> System.out.println("Exception " + e);
    }

    public static Runnable onComplete() {
        return () -> System.out.println("Completed...");
    }
}
