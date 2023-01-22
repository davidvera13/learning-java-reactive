package com.reactive.sec08PublishersCombinations;

import com.reactive.sec08PublishersCombinations.helper.NameGenerator;
import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class Lec01StartWith {
    public static void main(String[] args) {


        NameGenerator nameGenerator = new NameGenerator();
        nameGenerator
                .generateNames()
                .take(5)
                .subscribe(Utils.subscriber("First Subscriber"));

        // for 2nd subscription, we use cached values, except if new items are required (between 6 & 8)
        nameGenerator
                .generateNames()
                .take(8)
                .subscribe(Utils.subscriber("Second Subscriber"));

        // will generate one more
        nameGenerator
                .generateNames()
                .take(9)
                .subscribe(Utils.subscriber("Third Subscriber"));

        // will generate one more
        nameGenerator
                .generateNames()
                .filter(name -> name.startsWith("A"))
                .take(1)
                .subscribe(Utils.subscriber("Fourth Subscriber"));
    }
}
