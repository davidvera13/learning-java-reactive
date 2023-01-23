package com.reactive.sec10RepeatAndRetry;

import com.reactive.utils.Utils;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

public class Lec05RetryAdvanced {

    public static void main(String[] args) {
//        orderService(Utils.faker().business().creditCardNumber())
//                .doOnError(err -> System.out.println(err.getMessage()))
//                .retry(5)
//                .subscribe(Utils.subscriber());

        orderService(Utils.faker().business().creditCardNumber())
                .retryWhen(Retry.from(
                        flux -> flux
                                .doOnNext(rs -> {
                                    System.out.println(rs.totalRetries());
                                    System.out.println(rs.failure());
                                })
                                .handle((rs, synchronousSink) -> {
                                    if(rs.failure().getMessage().equals("500"))
                                        synchronousSink.next(1);
                                    else
                                        synchronousSink.error(rs.failure());
                                })
                                .delayElements(Duration.ofSeconds(1))
                ))
                .subscribe(Utils.subscriber());

        Utils.sleepSeconds(15);


    }

    // order service
    private static Mono<String> orderService(String ccNumber){
        return Mono.fromSupplier(() -> {
            processPayment(ccNumber);
            return Utils.faker().idNumber().valid();
        });
    }

    // payment service
    private static void processPayment(String ccNumber){
        int random = Utils.faker().random().nextInt(1, 10);
        if(random < 8)
            throw new RuntimeException("500");
        else if(random < 10)
            throw new RuntimeException("404");
    }
}
