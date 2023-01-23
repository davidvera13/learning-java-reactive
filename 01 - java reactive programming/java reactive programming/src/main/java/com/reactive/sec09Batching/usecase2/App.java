package com.reactive.sec09Batching.usecase2;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class App {
    public static void main(String[] args) {

        Map<String, Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>>> map = Map.of(
                "Kids", OrderProcessor.kidsProcessing(),
                "Automotive", OrderProcessor.automotiveProcessing()
        );

        Set<String> set = map.keySet();

        OrderService.orderStream()
                .filter(p -> set.contains(p.getCategory()))
                .groupBy(PurchaseOrder::getCategory)  // 2 keys
                .flatMap(gf -> map.get(gf.key()).apply(gf)) //flux
                .subscribe(Utils.subscriber());

        Utils.sleepSeconds(20);

    }

}
