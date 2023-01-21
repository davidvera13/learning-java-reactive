package com.reactive.sec02Flux;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

public class Lec04FluxFromStream {
    public static void main(String[] args) {
        List<Integer> integerList = List.of(1, 2, 3, 4, 5, 7, 13);
        Stream<Integer> stream = integerList.stream();
        stream.forEach(System.out::println);
        // can be consummed once only before being closed: IllegalStateException: stream has already been operated upon or closed
        // stream.forEach(System.out::println);
        System.out.println("*************");
        stream = integerList.stream();
        Flux<Integer> integerFlux = Flux.fromStream(stream);

        // multiple subscribers won't work ...
        integerFlux.subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());
        // IllegalStateException: stream has already been operated upon or closed
        integerFlux.subscribe(Utils.onNext(), Utils.onError(), Utils.onComplete());

        System.out.println("*************");
        // using supplier of stream ...
        stream = integerList.stream();
        Stream<Integer> finalStream = stream;
        // If we pass finalStream as value, the error will be the same. If we directly pass integerList.stream() no error
        // integerFlux = Flux.fromStream(() -> integerList.stream());
        integerFlux = Flux.fromStream(integerList::stream);

        // multiple subscribers won't work ...
        integerFlux.subscribe(
                Utils.onNext(),
                Utils.onError(),
                Utils.onComplete());
        // IllegalStateException: stream has already been operated upon or closed
        integerFlux.subscribe(
                Utils.onNext(),
                Utils.onError(),
                Utils.onComplete());

    }
}
