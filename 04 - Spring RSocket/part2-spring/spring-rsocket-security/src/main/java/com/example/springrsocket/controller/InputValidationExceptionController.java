package com.example.springrsocket.controller;

import com.example.springrsocket.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@MessageMapping("math.validation.handler")
public class InputValidationExceptionController {
    private final MathService mathService;

    @Autowired
    public InputValidationExceptionController(MathService mathService) {
        this.mathService = mathService;
    }

    // fire and forget
    @MessageMapping("printdouble.{input}")
    public Mono<Integer> doubleIt(@DestinationVariable int input) {
        System.out.println("Received input : " + input);
        return Mono.just(input)
                .filter(i -> i < 31)
                .map(i -> 2*i)
                .switchIfEmpty(Mono.error(new IllegalAccessException("Value should be less than 30")));
    }

    // in the controller class ...

    @MessageExceptionHandler
    public Mono<Integer> handleException(Exception e) {
        return Mono.just(-1);
    }
}
