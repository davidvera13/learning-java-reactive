package com.reactive.sec09Batching.usecase2;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Objective
 * let's consider we have to process orders from 2 different kind: automotive and kids.
 * the orders are handled in two different way:
 * > for automotive we have to add 10% VAT
 * > for kids we have 50% discount + free product in the order
 * each kids order
 *
 */
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
