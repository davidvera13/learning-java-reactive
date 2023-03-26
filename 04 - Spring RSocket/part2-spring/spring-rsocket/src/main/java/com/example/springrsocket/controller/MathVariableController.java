package com.example.springrsocket.controller;

import com.example.springrsocket.service.MathService;
import com.example.springrsocket.shared.dto.ChartResponseDto;
import com.example.springrsocket.shared.dto.ComputationRequestDto;
import com.example.springrsocket.shared.dto.ComputationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@MessageMapping("math.service")
public class MathVariableController {
    private final MathService mathService;

    @Autowired
    public MathVariableController(MathService mathService) {
        this.mathService = mathService;
    }

    // fire and forget
    @MessageMapping("print.{input}")
    public Mono<Void> print(@DestinationVariable int input) {
        System.out.println("Received input : " + input);
        return Mono.empty();
    }
}
