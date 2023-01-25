package com.reactive.ws.ui.controller;

import com.reactive.ws.ui.dto.ResponseDto;
import com.reactive.ws.ui.exceptions.InputValidationException;
import com.reactive.ws.ui.service.CalculationReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("validator")
public class ReactiveMathValidationController {
    private final CalculationReactiveService service;

    @Autowired
    public ReactiveMathValidationController(CalculationReactiveService service) {
        this.service = service;
    }

    @GetMapping("square/{input}/throw")
    public Mono<ResponseDto> findSquare(@PathVariable int input){
        if(input < 10 || input > 20) {
            throw new InputValidationException(input);
        }
        return this.service.findSquare(input);
    }

    @GetMapping("square/{input}/mono-error")
    public Mono<ResponseDto> monoError(@PathVariable int input){
        return Mono.just(input)
                .handle((integer, sink) -> {
                    if(integer >= 10 && integer <= 20)
                        sink.next(integer);
                    else
                        // exception is handled in the pipeline
                        sink.error(new InputValidationException(integer));
                })
                .cast(Integer.class)
                .flatMap(this.service::findSquare);
                //.flatMap(i -> this.service.findSquare(i));
    }

    @GetMapping("square/{input}/filter-values")
    public Mono<ResponseEntity<ResponseDto>> filteringValue(@PathVariable int input){
        // objective : returns a bad request, not an exception ... 
        return Mono.just(input)
                .filter(i -> i >= 10 && i <= 20)
                .flatMap(service::findSquare)
                //.flatMap(i -> service.findSquare(i))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
