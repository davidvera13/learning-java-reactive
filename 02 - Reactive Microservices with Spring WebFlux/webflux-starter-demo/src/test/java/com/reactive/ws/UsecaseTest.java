package com.reactive.ws;

import com.reactive.ws.ui.dto.CalculationRequestDto;
import com.reactive.ws.ui.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

public class UsecaseTest extends BaseTests {

    private final static String FORMAT = "%d %s %d = %s";
    private static final int A = 10;
    @Autowired
    private WebClient webClient;

    @Test
    public void test(){

        Flux<String> flux = Flux.range(1, 5)
                .flatMap(b -> Flux.just("+", "-", "*", "/")
                        .flatMap(op -> send(b, op)))
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(20)
                .verifyComplete();

    }

    private Mono<String> send(int b, String op){
        return this.webClient
                .get()
                .uri("calculator/{a}/{b}", A, b)
                .headers(h -> h.set("op", op))
                .retrieve()
                .bodyToMono(String.class)
                .map(v -> String.format(FORMAT, A, op, b, v));
    }

}
