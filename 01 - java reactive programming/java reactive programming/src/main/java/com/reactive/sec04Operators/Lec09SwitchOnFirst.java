package com.reactive.sec04Operators;

import com.reactive.utils.Person;
import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.util.function.Function;

public class Lec09SwitchOnFirst {
    public static void main(String[] args) {
        getPersons()
                .switchOnFirst(((signal, personFlux) -> {
                    System.out.println(">> switchOnFirst() called");
                    return signal.isOnNext() && signal.get().getAge() > 15 ? personFlux : applyFilterMap().apply(personFlux);
                }))
                .subscribe(Utils.subscriber());
    }

    public static Flux<Person> getPersons() {
        return Flux.range(1, 10)
                .map(i -> new Person());
    }

    public static Function<Flux<Person>, Flux<Person>> applyFilterMap() {
        return personFlux -> personFlux
                .filter(p -> p.getAge() > 10)
                .doOnNext(p -> p.setName(p.getName().toUpperCase()))
                .doOnDiscard(Person.class, p -> System.out.println("Not allowing:  " + p));
    }
}
