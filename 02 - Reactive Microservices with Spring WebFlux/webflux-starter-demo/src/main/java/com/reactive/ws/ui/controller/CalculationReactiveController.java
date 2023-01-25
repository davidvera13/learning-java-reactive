package com.reactive.ws.ui.controller;

import com.reactive.ws.ui.dto.ResponseDto;
import com.reactive.ws.ui.service.CalculationReactiveService;
import com.reactive.ws.ui.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.AbstractJackson2Encoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * this controller returns classic rest call and has delay for multiplication
 */
@RestController
@RequestMapping("reactiveCalc")
public class CalculationReactiveController {
    private final CalculationReactiveService service;

    @Autowired
    public CalculationReactiveController(CalculationReactiveService service) {
        this.service = service;
    }

    @GetMapping(value = "square/{input}")
    public Mono<ResponseDto> findSquare(@PathVariable("input") int input) {
        return service.findSquare(input);
    }

    @GetMapping(value = "multiplication/{input}")
    public Flux<ResponseDto> multiplicationTable(@PathVariable("input") int input) {
        // works like Mono<List<ResponseDto>>
        return service.multiplicationTable(input);
    }

    // data is returned as stream every second web page is updated until the end of the process
    // if we stop browser, the backend stops sending data
    @GetMapping(
            value = "multiplication/{input}/stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<ResponseDto> multiplicationTableStream(@PathVariable("input") int input) {
        return service.multiplicationTable(input);
    }
}
