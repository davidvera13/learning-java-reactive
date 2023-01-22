package com.reactive.sec08PublishersCombinations;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec05CombineLatest {
    public static void main(String[] args) {
        Flux
                .combineLatest(getStrings(), getNumbers(), (str, num) ->  str + num)
                .subscribe(Utils.subscriber());

        Utils.sleepSeconds(10);
    }
    private static Flux<String> getStrings() {
        return Flux.just("A", "B", "C", "D")
                .delayElements(Duration.ofSeconds(1));
    }
    private static Flux<Integer> getNumbers() {
        return Flux.just(1, 2, 3)
                .delayElements(Duration.ofSeconds(1));
    }
}
