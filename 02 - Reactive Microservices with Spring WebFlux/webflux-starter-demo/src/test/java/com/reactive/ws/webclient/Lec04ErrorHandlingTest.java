package com.reactive.ws.webclient;


import com.reactive.ws.ui.controller.ReactiveMathValidationController;
import com.reactive.ws.ui.dto.ResponseDto;
import com.reactive.ws.ui.service.CalculationReactiveService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathValidationController.class)
public class Lec04ErrorHandlingTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private CalculationReactiveService calculationReactiveService;

    @Test
    public void errorHandlingTest(){
        Mockito.when(calculationReactiveService.findSquare(Mockito.anyInt())).thenReturn(Mono.just(new ResponseDto(1)));
        this.client
                .get()
                .uri("/validator/square/{number}/throw", 5) // 10-20
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("allowed range is 10 - 20")
                .jsonPath("$.errorCode").isEqualTo(100)
                .jsonPath("$.input").isEqualTo(5);
    }

}