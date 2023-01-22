package com.reactive.sec04Operators;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

public class Lec02DoHooksCallback {
    public static void main(String[] args) {
        // note the order of the doFirst : reverse order
        // order of doOnSuscribe is in the order
        // doOnCancel is not triggered. We could trigger it by adding take() operator
        // doOnDiscard allow to retrieve emitted data when subscriber unsubscribed and cancel
        Flux
                .create(fluxSink -> {
                    System.out.println("Inside create");
                    for (int i = 0; i < 5; i++)
                        fluxSink.next(i);
                    fluxSink.complete();
                    System.out.println("--complete--");
                })
                .doOnComplete(() -> System.out.println("doOnComplete"))
                .doFirst(() -> System.out.println("doFirst v1"))
                // do require a consumer, actually, requires the object used in the fluxSink
                .doOnNext(obj -> System.out.println("doOnNext: " + obj))
                .doOnSubscribe(obj -> System.out.println("doOnSubscribe v1: " + obj))
                // obj is actuallya long value
                .doOnRequest(obj -> System.out.println("doOnRequest: " + obj))
                .doFirst(() -> System.out.println("doFirst v2"))
                .doOnError(err -> System.out.println("doOnError: " + err.getMessage()))
                .doOnTerminate(() -> System.out.println("doOnTerminate"))
                .doFirst(() -> System.out.println("doFirst v3"))
                .doOnCancel(() -> System.out.println("doOnCancel"))
                .doOnSubscribe(obj -> System.out.println("doOnSubscribe v2: " + obj))
                .doFinally(signalType -> System.out.println("doFinally v1: " + signalType))
                .doOnDiscard(Object.class, o -> System.out.println("doOnDiscard: " + o))
                // .take(3)
                .doFinally(signalType -> System.out.println("doFinally v2: " + signalType))
                .subscribe(Utils.subscriber());
    }
}
