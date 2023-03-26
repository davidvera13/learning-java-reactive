package com.example.springrsocket.controller;

import com.example.springrsocket.service.MathService;
import com.example.springrsocket.shared.dto.ChartResponseDto;
import com.example.springrsocket.shared.dto.ComputationRequestDto;
import com.example.springrsocket.shared.dto.ComputationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class MathController {
    private final MathService mathService;

    @Autowired
    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    // fire and forget
    @MessageMapping("math.service.print")
    public Mono<Void> print(Mono<ComputationRequestDto> requestDtoMono) {
        return mathService.print(requestDtoMono);
    }

    // request response
    @MessageMapping("math.service.calculateSquare")
    public Mono<ComputationResponseDto> calculateSquare(Mono<ComputationRequestDto> requestDtoMono) {
        return mathService.calculateSquare(requestDtoMono);
    }

    // request stream
    @MessageMapping("math.service.multiplicationTableStream")
    public Flux<ComputationResponseDto> multiplicationTableStream(Mono<ComputationRequestDto> requestDtoMono) {
        // Mono must be converted to flux
        return requestDtoMono
                .flatMapMany(this.mathService::multiplicationTableStream);
    }

    // request channel
    @MessageMapping("math.service.chartStream")
    public Flux<ChartResponseDto> chartStream(Flux<ComputationRequestDto> requestDtoFlux) {
        return mathService.chartStream(requestDtoFlux);
    }
}
