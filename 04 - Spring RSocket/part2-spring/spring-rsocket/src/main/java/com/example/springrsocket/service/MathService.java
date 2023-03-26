package com.example.springrsocket.service;

import com.example.springrsocket.shared.dto.ChartResponseDto;
import com.example.springrsocket.shared.dto.ComputationRequestDto;
import com.example.springrsocket.shared.dto.ComputationResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MathService {
    // fire and forget
    Mono<Void> print(Mono<ComputationRequestDto> requestDtoMono);

    // request response
    Mono<ComputationResponseDto> calculateSquare(Mono<ComputationRequestDto> requestDtoMono);

    // request stream
    Flux<ComputationResponseDto> multiplicationTableStream(ComputationRequestDto requestDto);

    // request channel
    Flux<ChartResponseDto> chartStream(Flux<ComputationRequestDto> requestDtoFlux);
}
