package com.reactive.sec03;

import com.reactive.utils.NameProducer;
import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec02FluxCreateAlternative {
    public static void main(String[] args) {
        NameProducer nameProducer = new NameProducer();
        Flux.create(nameProducer)
                .subscribe(Utils.subscriber("RefactorFluxCreation"));

        nameProducer.produce();

    }
}
