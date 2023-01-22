package com.reactive.sec08PublishersCombinations.usecase;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class CarPriceEvolution {
    public static void main(String[] args) {
        int initialCarPrice = 10000;
        Flux
                .combineLatest(monthStream(), demandStream(), (month, demand) -> {
                    // a car loose $100 each month... and the demand reduce or accelerate the used car sell price
                    return initialCarPrice - (month * 100) * demand;
                })
                .subscribe(Utils.subscriber());
        Utils.sleepSeconds(30);
    }

    private static Flux<Long> monthStream(){
        // months will start immediately, and will increase by 1 each seconds (for test purpose)
        return Flux.interval(Duration.ZERO, Duration.ofSeconds(1));
    }

    private static Flux<Double> demandStream(){
        // every quarter, the demand for a car model can influence the price of a used car
        // coefficient is betwen 0.8 and 1.2
        // at start, the multiplicator will be equal to 1
        return Flux.interval(Duration.ofSeconds(3))
                .map(i -> Utils.faker().random().nextInt(80, 120) / 100d)
                .startWith(1d);
    }
}
