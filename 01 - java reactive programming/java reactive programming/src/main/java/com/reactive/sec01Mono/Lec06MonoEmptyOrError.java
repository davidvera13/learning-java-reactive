package com.reactive.sec01Mono;

import com.reactive.utils.Utils;
import reactor.core.publisher.Mono;

public class Lec06MonoEmptyOrError {
    public static void main(String[] args) {
        userRepository(1)
                .subscribe(
                        Utils.onNext(),
                        Utils.onError(),
                        Utils.onComplete());

        // directly reach completed, userRepository return empty
        userRepository(2)
                .subscribe(
                        Utils.onNext(),
                        Utils.onError(),
                        Utils.onComplete());


        // directly throws exception
        userRepository(3)
                .subscribe(
                        Utils.onNext(),
                        Utils.onError(),
                        Utils.onComplete());


    }


    // faking repository
    private static Mono<String> userRepository(int userId) {
        if (userId == 1)
            return Mono.just(Utils.faker().name().fullName());
        else if(userId == 2)
            return Mono.empty();
            // return empty, not null
            // return null;
        else
            return Mono.error(new RuntimeException("Out of allowed user ranges..."));

    }
}
