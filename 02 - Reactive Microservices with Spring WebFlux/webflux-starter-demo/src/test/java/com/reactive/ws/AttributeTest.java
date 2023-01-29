package com.reactive.ws;

import com.reactive.ws.BaseTests;
import com.reactive.ws.ui.dto.CalculationRequestDto;
import com.reactive.ws.ui.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class AttributeTest extends BaseTests {

    @Autowired
    private WebClient webClient;

    @Test
    void headerBasicAuth() {
        // sum, substract, multiply ,divide
        CalculationRequestDto requestDto = buildOperation(12, 8, "multiply");
        Mono<ResponseDto> responseDtoMono = this.webClient
                .post()
                .uri("http://localhost:8080/reactiveCalc/operation")
                .bodyValue(requestDto)
                // .headers(h -> h.set("some-key", "some-other-value"))
                .attribute("auth", "basic")
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseDtoMono)
                .expectNextMatches(responseDto -> responseDto.getOutput() == 96)
                .verifyComplete();
    }

    @Test
    void headerJwtAuth() {
        // sum, substract, multiply ,divide
        CalculationRequestDto requestDto = buildOperation(12, 8, "multiply");
        Mono<ResponseDto> responseDtoMono = this.webClient
                .post()
                .uri("http://localhost:8080/reactiveCalc/operation")
                .bodyValue(requestDto)
                // .headers(h -> h.set("some-key", "some-other-value"))
                .attribute("auth", "oauth")
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseDtoMono)
                .expectNextMatches(responseDto -> responseDto.getOutput() == 96)
                .verifyComplete();
    }


    // we do not send any authorization ...
    @Test
    void headerNoAuthAttributes() {
        // sum, substract, multiply ,divide
        CalculationRequestDto requestDto = buildOperation(12, 8, "multiply");
        Mono<ResponseDto> responseDtoMono = this.webClient
                .post()
                .uri("http://localhost:8080/reactiveCalc/operation")
                .bodyValue(requestDto)
                //.attribute("auth", "oauth")
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
}
