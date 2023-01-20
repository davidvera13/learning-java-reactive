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
        System.out.println("******************");
        // Runnable runnable = () -> nameProducer.produce();
        Runnable runnable = nameProducer::produce;
        for (int i = 0; i < 10; i++) {
            // running 10 threads
            new Thread(runnable).start();
        }

        Utils.sleepSeconds(3);
    }
}
