package com.example.springrsocket.usecase;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;

public class Player {
    private final Sinks.Many<Integer> sinks = Sinks.many().unicast().onBackpressureBuffer();
    private int lowerBound = 0;
    private int upperBound = 100;
    private int midValue = 0; // at startup
    private int attempts = 0;


    public void play() {
        this.emit();
    }

    public Flux<Integer> guesses() {
        return this.sinks.asFlux();
    }

    public Consumer<GuessNumberResponseEnum> receives() {
        return this::processResponse;
    }

    private void processResponse(GuessNumberResponseEnum guessNumberResponse) {
        attempts++;
        System.out.println("Number of attempts " + attempts + "\tproposed number " + midValue + "\tvalue is " + guessNumberResponse);
        if(GuessNumberResponseEnum.EQUAL.equals(guessNumberResponse)) {
            this.sinks.tryEmitComplete();
            return; // we have nothing to do from here
        }

        if(GuessNumberResponseEnum.GREATER.equals(guessNumberResponse))
            lowerBound = midValue;
        else if(GuessNumberResponseEnum.LESSER.equals(guessNumberResponse))
            upperBound = midValue;
        this.emit();
    }

    void emit() {
        midValue = lowerBound + (upperBound - lowerBound)/ 2;
        // emit..
        this.sinks.tryEmitNext(midValue);
    }
}
