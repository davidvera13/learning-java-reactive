package com.reactive.sec01Mono;

import com.reactive.utils.Utils;
import reactor.core.publisher.Mono;

public class Lec13MonoFromRunnable {
    public static void main(String[] args) {
        // it takes no parameter and return nothing.
        // When to use runnable ? We may have operation that take some time and we expect a notification.
        Runnable runnable = () -> System.out.println("Something happens...");
        Mono.fromRunnable(runnable)
                .subscribe(
                        Utils.onNext(),
                        Utils.onError(),
                        Utils.onComplete());

        // if calling time consumming method
        Mono.fromRunnable(timeConsumingProcess())
                .subscribe(
                        Utils.onNext(),
                        Utils.onError(),
                        () -> System.out.println("Process completed... Sending mails to whole team"));
    }


    private static Runnable timeConsumingProcess() {
        return () -> {
            Utils.sleepSeconds(3);
            System.out.println("We took time to do process");
        };
    }


}
