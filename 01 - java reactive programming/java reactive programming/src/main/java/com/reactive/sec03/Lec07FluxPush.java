package com.reactive.sec03;

import com.reactive.utils.NameProducer;
import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec07FluxPush {
    public static void main(String[] args) {
        NameProducer nameProducer = new NameProducer();
        // using push instead of create

        // create is thread safe, push is not thread safe ! be aware
        // some output may be missing... 
        Flux.push(nameProducer)
                .subscribe(Utils.subscriber());
        Runnable runnable = nameProducer::produce;
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }
}
