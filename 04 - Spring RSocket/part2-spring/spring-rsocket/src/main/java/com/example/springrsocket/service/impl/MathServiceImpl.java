package com.example.springrsocket.service.impl;

import com.example.springrsocket.service.MathService;
import com.example.springrsocket.shared.dto.ChartResponseDto;
import com.example.springrsocket.shared.dto.ComputationRequestDto;
import com.example.springrsocket.shared.dto.ComputationResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MathServiceImpl implements MathService {
    // fire and forget
    @Override
    public Mono<Void> print(Mono<ComputationRequestDto> requestDtoMono) {
        return requestDtoMono
                .doOnNext(System.out::println)
                .then();
    }

    // request response
    @Override
    public Mono<ComputationResponseDto> calculateSquare(Mono<ComputationRequestDto> requestDtoMono) {
        return requestDtoMono
                .map(ComputationRequestDto::getInput)
                .map(i -> new ComputationResponseDto(i, (int) Math.pow(i, 2)));
    }

    // request stream
    @Override
    public Flux<ComputationResponseDto> multiplicationTableStream(ComputationRequestDto requestDto) {
        return Flux.range(0, 10)
                .map(i -> new ComputationResponseDto(requestDto.getInput(), requestDto.getInput() * i));
    }

    // request channel
    @Override
    public Flux<ChartResponseDto> chartStream(Flux<ComputationRequestDto> requestDtoFlux) {
        return requestDtoFlux
                .map(ComputationRequestDto::getInput)
                .map(i -> new ChartResponseDto(i, (int) Math.pow(i, 2) + 1));
    }
}

