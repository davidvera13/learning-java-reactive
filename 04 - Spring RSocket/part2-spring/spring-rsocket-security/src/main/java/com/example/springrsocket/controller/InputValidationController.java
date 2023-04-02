package com.example.springrsocket.controller;

import com.example.springrsocket.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@MessageMapping("math.validation")
public class InputValidationController {
    private final MathService mathService;

    @Autowired
    public InputValidationController(MathService mathService) {
        this.mathService = mathService;
    }

    // fire and forget
    @MessageMapping("printdouble.{input}")
    public Mono<Integer> doubleIt(@DestinationVariable int input) {
        System.out.println("Received input : " + input);
        if(input < 31)
            return Mono.just(input * 2);
        else
            return Mono.error(new IllegalAccessException("Value should be less than 30"));
    }
}
