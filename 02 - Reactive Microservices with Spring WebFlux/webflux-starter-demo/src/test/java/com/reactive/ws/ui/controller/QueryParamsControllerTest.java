package com.reactive.ws.ui.controller;

import com.reactive.ws.BaseTests;
import com.reactive.ws.utils.WebClientConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QueryParamsControllerTest extends BaseTests {
    @Autowired
    private WebClient webClient;
    public static final String URL = "http://localhost:8080/dummy/search?count={count}&page={page}";

    @Test
    void searchJobQueryParamsTest() {
        URI uri = UriComponentsBuilder.fromUriString(URL).build(12, 5);
        Flux<Integer>  integerFlux = this.webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void searchJobQueryParams2Test() {
        Flux<Integer>  integerFlux = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("dummy/search")
                        .query("count={count}&page={page}")
                        .build(10, 20))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }


    @Test
    void searchJobQueryParams3Test() {
        Map<String, Integer> map = Map.of(
                "count", 10,
                "page", 20
        );

        Flux<Integer>  integerFlux = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("dummy/search")
                        .query("count={count}&page={page}")
                        .build(map))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}