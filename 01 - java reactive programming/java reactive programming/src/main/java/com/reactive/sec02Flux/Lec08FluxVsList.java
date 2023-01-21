package com.reactive.sec02Flux;

import com.reactive.sec02Flux.helper.NameGenerator;
import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec08FluxVsList {
    public static void main(String[] args) {
        List<String> names = NameGenerator.getNames(5);
        // we wait 5 seconds before displaying all names
        System.out.println(names);

        System.out.println("******************");
        Flux<String> namesFlux = NameGenerator.getFluxNames(5);
        // each second a name is printed
        namesFlux.subscribe(
                Utils.onNext(), Utils.onError(), Utils.onComplete());


    }
}
