package com.example.springrsocket.controller;

import com.example.springrsocket.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@MessageMapping("math.error")
public class ErrorReturnController {
    private final MathService mathService;

    @Autowired
    public ErrorReturnController(MathService mathService) {
        this.mathService = mathService;
    }


    @MessageMapping("printdouble.{input}")
    public Mono<Integer> doubleIt(@DestinationVariable int input) {
        System.out.println("Received input : " + input);
        return Mono.just(input)
                .filter(i -> i < 30)
                .map(i -> i * 2);
        // if(input < 31)
        //     return Mono.just(input * 2);
        // else
        //     return Mono.error(new IllegalAccessException("Value should be less than 30"));
    }

    @MessageMapping("printdouble.defaultIfEmtpy.{input}")
    public Mono<Integer> doubleItWithDefaultValue(@DestinationVariable int input) {
        System.out.println("Received input : " + input);
        return Mono.just(input)
                .filter(i -> i < 30)
                .map(i -> i * 2)
                .defaultIfEmpty(Integer.MIN_VALUE);
    }

    @MessageMapping("printdouble.switchIfEmpty.{input}")
    public Mono<Integer> doubleItWithDefaultSwitchValue(@DestinationVariable int input) {
        System.out.println("Received input : " + input);
        return Mono.just(input)
                .filter(i -> i < 30)
                .map(i -> i * 2)
                .switchIfEmpty(Mono.error(new IllegalAccessException("Value should be less than 30")));
    }
}
