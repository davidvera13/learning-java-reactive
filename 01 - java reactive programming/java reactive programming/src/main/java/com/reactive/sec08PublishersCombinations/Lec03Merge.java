package com.reactive.sec08PublishersCombinations;

import com.reactive.sec08PublishersCombinations.helper.AmericanAirlines;
import com.reactive.sec08PublishersCombinations.helper.Emirates;
import com.reactive.sec08PublishersCombinations.helper.Qatar;
import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec03Merge {
    public static void main(String[] args) {
        Flux<String> merge = Flux
                .merge(
                        Qatar.getFlights(),
                        AmericanAirlines.getFlights(),
                        Emirates.getFlights());
        merge.subscribe(Utils.subscriber());
        Utils.sleepSeconds(10);
    }
}
