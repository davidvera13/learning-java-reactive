package com.reactive.sec02;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class Lec03FluxFromArrayOrList {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("a", "b", "c", "d", "e", "f");
        Flux.fromIterable(strings).subscribe(Utils.onNext());

        System.out.println("****************");
        Integer[] ints = { 2, 5, 7, 13, 22};
        Flux.fromArray(ints).subscribe(Utils.onNext());
    }
}
