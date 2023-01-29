package com.reactive.ws.ui.controller;

import com.reactive.ws.BaseTests;
import com.reactive.ws.ui.dto.ErrorResponse;
import com.reactive.ws.ui.dto.ResponseDto;
import com.reactive.ws.ui.exceptions.InputValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class ReactiveMathValidationControllerTest extends BaseTests {
    @Autowired
    private WebClient webClient;

    @Test
    void badRequestTest() {
        Mono<ResponseDto> responseDtoMono = this.webClient
                .get()
                .uri("http://localhost:8080/validator/square/{input}/throw", 2)
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .doOnNext(System.out::println) /// printing result of multiplication
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(responseDtoMono)
                .verifyError(WebClientResponseException.BadRequest.class);
    }


    @Test
    void badRequestExchangeTest() {
        Mono<Object> objectMono = this.webClient
                .get()
                .uri("http://localhost:8080/validator/square/{input}/throw", 2)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println) /// printing result of multiplication
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(objectMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void badRequestValidationExchangeSuccessTest() {
        Mono<Object> objectMono = this.webClient
                .get()
                .uri("http://localhost:8080/validator/square/{input}/throw", 11)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println) /// printing result of multiplication
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(objectMono)
                .expectNextCount(1)
                .verifyComplete();
    }


    private Mono<Object> exchange(ClientResponse clientResponse) {
        if(clientResponse.statusCode().value() == 400)
            return clientResponse.bodyToMono(ErrorResponse.class);
        else
            return clientResponse.bodyToMono(ResponseDto.class);
    }


}