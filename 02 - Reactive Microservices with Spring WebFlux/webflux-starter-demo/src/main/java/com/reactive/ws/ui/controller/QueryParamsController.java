package com.reactive.ws.ui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("dummy")
public class QueryParamsController {
    @GetMapping(value = "search")
    public Flux<Integer> searchJob(@RequestParam int count, @RequestParam int page) {
        // just retutning values of query params
        return Flux.just(count, page);
    }
}
