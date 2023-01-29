package com.reactive.ws.ui.controller;

import com.reactive.ws.BaseTests;
import com.reactive.ws.ui.dto.CalculationRequestDto;
import com.reactive.ws.ui.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class CalculationReactiveControllerTest extends BaseTests {
    @Autowired
    private WebClient webClient;

    // Lec01
    @Test
    public void blockTest() {
        // we use block to retrieve the response object
        ResponseDto responseDto = this.webClient
                .get()
                .uri("http://localhost:8080/reactiveCalc/square/{input}", 5)
                .retrieve()
                .bodyToMono(ResponseDto.class) // expecting ResponseDto object
                .block();
        System.out.println(responseDto);
    }

    @Test
    public void steVerifierTest() {
        // we use block to retrieve the response object
        Mono<ResponseDto> responseDtoMono = this.webClient
                .get()
                .uri("http://localhost:8080/reactiveCalc/square/{input}", 5)
                .retrieve()
                .bodyToMono(ResponseDto.class);

        StepVerifier.create(responseDtoMono)
                .expectNextMatches(responseDto -> responseDto.getOutput() == 25)
                .verifyComplete();
    }

    @Test
    void fluxTest() {
        Flux<ResponseDto> responseDtoFlux = this.webClient
                .get()
                .uri("http://localhost:8080/reactiveCalc/multiplication/{input}", 5)
                .retrieve()
                .bodyToFlux(ResponseDto.class)
                .doOnNext(System.out::println); /// printing result of multiplication

        StepVerifier.create(responseDtoFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    void fluxStreamTest() {
        Flux<ResponseDto> responseDtoFlux = this.webClient
                .get()
                .uri("http://localhost:8080/reactiveCalc/multiplication/{input}/stream", 5)
                .retrieve()
                .bodyToFlux(ResponseDto.class)
                .doOnNext(System.out::println); /// printing result of multiplication

        StepVerifier.create(responseDtoFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    void postTest() {

        //  {
        //     "a": 12,
        //     "b": 0,
        //     "operation": "sum"
        //  }

        CalculationRequestDto requestDto = buildOperation(12, 8, "sum");
        Mono<ResponseDto> responseDtoMono = this.webClient
                .post()
                .uri("http://localhost:8080/reactiveCalc/operation")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(ResponseDto.class);

        StepVerifier.create(responseDtoMono)
                .expectNextMatches(responseDto -> responseDto.getOutput() == 20)
                .verifyComplete();
    }

    @Test
    void headersTest() {
        // sum, substract, multiply ,divide
        CalculationRequestDto requestDto = buildOperation(12, 8, "multiply");
        Mono<ResponseDto> responseDtoMono = this.webClient
                .post()
                .uri("http://localhost:8080/reactiveCalc/operation")
                .bodyValue(requestDto)
                .headers(h -> h.set("some-key", "some-other-value"))
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseDtoMono)
                .expectNextMatches(responseDto -> responseDto.getOutput() == 96)
                .verifyComplete();
    }


    private CalculationRequestDto buildOperation(int a, int b, String operation) {
        CalculationRequestDto calculationRequestDto = new CalculationRequestDto();
        calculationRequestDto.setA(a);
        calculationRequestDto.setB(b);
        calculationRequestDto.setOperation(operation);

        return calculationRequestDto;

    }


    // try
    @Test
    void headersAuthTokenTest() {
        // sum, substract, multiply ,divide
        CalculationRequestDto requestDto = buildOperation(12, 8, "multiply");
        Mono<ResponseDto> responseDtoMono = this.webClient
                .post()
                .uri("http://localhost:8080/reactiveCalc/operation")
                .bodyValue(requestDto)
                .headers(h -> h.set("some-key", "some-other-value"))
                // .headers(h -> h.setBasicAuth("username", "password"))
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseDtoMono)
                .expectNextMatches(responseDto -> responseDto.getOutput() == 96)
                .verifyComplete();

        // invoke each time token
        StepVerifier.create(responseDtoMono)
                .expectNextMatches(responseDto -> responseDto.getOutput() == 96)
                .verifyComplete();
    }

}