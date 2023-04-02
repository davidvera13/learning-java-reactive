package com.example.springrsocket;

import com.example.springrsocket.shared.errors.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec06InputValidationExceptionHandlerResponseTest {
    private RSocketRequester requester;

    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeAll
    public void setup(){
        this.requester = this.builder
                .transport(TcpClientTransport.create("localhost", 6565));
    }

    // fire and forget
    @Test
    void printDouble() {
        Mono<ResponseDto<Integer>> mono = this.requester
                .route("math.validation.handler.response.printdouble.20")
                .retrieveMono(new ParameterizedTypeReference<ResponseDto<Integer>>() {})
                .doOnNext(response -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new JavaTimeModule());
                    if(response.hasErrors()) {
                        System.out.println(response.getErrorResponse().getStatusCode().getMessage());
                        try {
                            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println(response.getSuccessResponse());
                        try {
                            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void printDoubleThrow() {
        Mono<ResponseDto<Integer>> mono = this.requester
                .route("math.validation.handler.response.printdouble.47")
                .retrieveMono(new ParameterizedTypeReference<ResponseDto<Integer>>() {})
                .doOnNext(response -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new JavaTimeModule());
                    if(response.hasErrors()) {
                        System.out.println(response.getErrorResponse().getStatusCode().getMessage());
                        try {
                            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println(response.getSuccessResponse());
                        try {
                            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }
}
